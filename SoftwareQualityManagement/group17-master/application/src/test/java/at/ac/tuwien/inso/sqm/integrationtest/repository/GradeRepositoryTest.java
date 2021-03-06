package at.ac.tuwien.inso.sqm.integrationtest.repository;

import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.MarkEntity;
import at.ac.tuwien.inso.sqm.entity.Semester;
import at.ac.tuwien.inso.sqm.entity.SemesterTypeEnum;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.repository.CourseRepository;
import at.ac.tuwien.inso.sqm.repository.GradeRepository;
import at.ac.tuwien.inso.sqm.repository.LecturerRepository;
import at.ac.tuwien.inso.sqm.repository.SemestreRepository;
import at.ac.tuwien.inso.sqm.repository.StduentRepository;
import at.ac.tuwien.inso.sqm.repository.SubjectRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class GradeRepositoryTest {

    @Autowired
    private StduentRepository stduentRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SemestreRepository semesterRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private LecturerRepository lecturerRepository;

    private List<StudentEntity> students;

    @Before
    public void setUp() throws Exception {
        students = StreamSupport.stream((stduentRepository.save(asList(
                new StudentEntity("123", "student", "student123@uis.at"),
                new StudentEntity("456", "student", "student456@uis.at"))))
                .spliterator(), false).collect(Collectors.toList());
    }

    @Test
    public void findAllOfStudentWithNoGrades() throws Exception {
        prepareGradeFor(students.get(0));

        List<Grade> grades = gradeRepository.findAllOfStudent(students.get(1));

        assertThat(grades, empty());
    }

    private Grade prepareGradeFor(StudentEntity student) {
        Subject subject =
                subjectRepository.save(new Subject("subject", BigDecimal.ONE));
        Semester semester = semesterRepository
                .save(new Semester(2016, SemesterTypeEnum.SummerSemester));
        Course course =
                courseRepository.save(new Course(subject, semester));
        LecturerEntity lecturer = lecturerRepository
                .save(new LecturerEntity("abc", "lecturer", "lecturer@uis.at"));

        return gradeRepository.save(new Grade(course, lecturer, student,
                MarkEntity.EXCELLENT));
    }

    @Test
    public void findAllOfStudentWithSomeFeedbackEntries() throws Exception {
        Grade grade = prepareGradeFor(students.get(0));

        List<Grade> grades = gradeRepository.findAllOfStudent(students.get(0));

        assertThat(grades, equalTo(singletonList(grade)));
    }
}
