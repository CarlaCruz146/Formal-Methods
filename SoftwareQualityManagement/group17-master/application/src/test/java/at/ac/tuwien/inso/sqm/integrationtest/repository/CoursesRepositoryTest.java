package at.ac.tuwien.inso.sqm.integrationtest.repository;

import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.MarkEntity;
import at.ac.tuwien.inso.sqm.entity.Semester;
import at.ac.tuwien.inso.sqm.entity.SemesterTypeEnum;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.Tag;
import at.ac.tuwien.inso.sqm.repository.CourseRepository;
import at.ac.tuwien.inso.sqm.repository.GradeRepository;
import at.ac.tuwien.inso.sqm.repository.LecturerRepository;
import at.ac.tuwien.inso.sqm.repository.SemestreRepository;
import at.ac.tuwien.inso.sqm.repository.StduentRepository;
import at.ac.tuwien.inso.sqm.repository.SubjectRepository;
import at.ac.tuwien.inso.sqm.repository.TagRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CoursesRepositoryTest {

    private final Map<String, Tag> tags = new HashMap<String, Tag>() {
        {
            put("Tag1", new Tag("Tag1"));
            put("Tag2", new Tag("Tag2"));
            put("Tag3", new Tag("Tag3"));
        }
    };
    private final Map<String, Subject> subjects =
            new HashMap<String, Subject>() {
                {
                    put("Subject1",
                            new Subject("Subject1", new BigDecimal(3.0)));
                    put("Subject2",
                            new Subject("Subject2", new BigDecimal(6.0)));
                    put("Subject3",
                            new Subject("Subject3", new BigDecimal(6.0)));
                    put("Subject4",
                            new Subject("Subject3", new BigDecimal(6.0)));
                    put("Subject5",
                            new Subject("Subject3", new BigDecimal(6.0)));
                }
            };
    private final Map<String, Semester> semesters =
            new HashMap<String, Semester>() {
                {
                    put("WS2015", new Semester(2015,
                            SemesterTypeEnum.WinterSemester));
                    put("WS2016", new Semester(2016,
                            SemesterTypeEnum.WinterSemester));
                }
            };
    private final Map<String, Course> courses =
            new HashMap<String, Course>() {
                {
                    put("Course1",
                            new Course(subjects.get("Subject1"),
                                    semesters.get("WS2016")));
                    put("Course2",
                            new Course(subjects.get("Subject2"),
                                    semesters.get("WS2016")));
                    put("Course3",
                            new Course(subjects.get("Subject3"),
                                    semesters.get("WS2016")));

                    put("Course4",
                            new Course(subjects.get("Subject4"),
                                    semesters.get("WS2015")));
                    put("Course5",
                            new Course(subjects.get("Subject5"),
                                    semesters.get("WS2015")));
                }
            };
    @Autowired
    private StduentRepository stduentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SemestreRepository semesterRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private LecturerRepository lecturerRepository;
    private List<StudentEntity> students;
    private LecturerEntity lecturer;

    private void addTags() {
        courses.get("Course1")
                .addTags(tags.get("Tag1"), tags.get("Tag2"), tags.get("Tag3"));
        courses.get("Course2").addTags(tags.get("Tag1"), tags.get("Tag2"));
        courses.get("Course3").addTags(tags.get("Tag1"));
    }

    @Before
    public void setUp() throws Exception {
        students = StreamSupport.stream((stduentRepository.save(asList(
                new StudentEntity("123", "student", "student123@uis.at"),
                new StudentEntity("456", "student", "student456@uis.at"))))
                .spliterator(), false).collect(Collectors.toList());
        lecturer = lecturerRepository
                .save(new LecturerEntity("l123", "lecturer",
                        "lecturer@uis.at"));

        tagRepository.save(tags.values());
        subjectRepository.save(subjects.values());
        semesterRepository.save(semesters.values());
        courseRepository.save(courses.values());

        addTags();
    }

    @Test
    public void verifyRecommendableCoursesForStudent() throws Exception {
        List<Course> actual = courseRepository
                .findAllRecommendableForStudent(students.get(0));

        assertThat(actual, CoreMatchers
                .hasItems(courses.get("Course1"), courses.get("Course2"),
                        courses.get("Course3")));
    }

    @Test
    public void verifyRecommendableCoursesForStudentWithRegistrations()
            throws Exception {
        StudentEntity student = students.get(0);
        courses.get("Course1").addStudents(student);

        List<Course> actual =
                courseRepository.findAllRecommendableForStudent(student);

        assertThat(actual, not(hasItem(courses.get("Course1"))));
    }

    @Test
    public void verifyRecommendableCoursesForStudentWithNegativeGrade()
            throws Exception {
        addGradeForCourseInOlderSemester(MarkEntity.FAILED);

        List<Course> actual = courseRepository
                .findAllRecommendableForStudent(students.get(0));

        assertThat(actual, hasItem(courses.get("Course1")));
    }

    private void addGradeForCourseInOlderSemester(MarkEntity mark) {
        Course olderCourse = courseRepository
                .save(new Course(courses.get("Course1").getSubject(),
                        semesters.get("WS2015")));

        gradeRepository
                .save(new Grade(olderCourse, lecturer, students.get(0), mark));
    }

    @Test
    public void verifyRecommendableCoursesForStudentWithPositiveGrade()
            throws Exception {
        addGradeForCourseInOlderSemester(MarkEntity.SATISFACTORY);

        List<Course> actual = courseRepository
                .findAllRecommendableForStudent(students.get(0));

        assertThat(actual, not(hasItem(courses.get("Course1"))));
    }

    @Test
    public void findAllForStudentWithEmptyCourses() throws Exception {
        courses.values().forEach(it -> it.addStudents(students.get(0)));

        List<Course> courses =
                courseRepository.findAllForStudent(students.get(1));

        assertThat(courses, empty());
    }

    @Test
    public void findAllForStudentWithCourseRegistrations() throws Exception {
        Course course = courses.get("Course1");
        StudentEntity student = students.get(0);
        course.addStudents(student);

        List<Course> courses =
                courseRepository.findAllForStudent(student);

        assertThat(courses, equalTo(singletonList(course)));
    }

    @Test
    public void testExistsCourseRegistrationForStudentRegisteredToCourse()
            throws Exception {
        Course course = courses.get("Course1");
        StudentEntity student = students.get(0);
        course.addStudents(student);

        assertTrue(courseRepository.existsCourseRegistration(student, course));
    }

    @Test
    public void testExistsCourseRegistrationForStudentNotRegisteredToCourse()
            throws Exception {
        Course course = courses.get("Course1");
        StudentEntity student = students.get(0);
        course.addStudents(student);

        assertFalse(courseRepository
                .existsCourseRegistration(student, courses.get("Course2")));
    }
}
