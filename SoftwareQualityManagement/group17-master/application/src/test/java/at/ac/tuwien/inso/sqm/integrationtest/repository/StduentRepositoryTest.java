package at.ac.tuwien.inso.sqm.integrationtest.repository;

import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.Role;
import at.ac.tuwien.inso.sqm.entity.Semester;
import at.ac.tuwien.inso.sqm.entity.SemesterTypeEnum;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.Tag;
import at.ac.tuwien.inso.sqm.entity.UserAccountEntity;
import at.ac.tuwien.inso.sqm.repository.CourseRepository;
import at.ac.tuwien.inso.sqm.repository.SemestreRepository;
import at.ac.tuwien.inso.sqm.repository.StduentRepository;
import at.ac.tuwien.inso.sqm.repository.SubjectRepository;
import at.ac.tuwien.inso.sqm.repository.TagRepository;
import at.ac.tuwien.inso.sqm.repository.utils.TagFrequency;
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

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StduentRepositoryTest {

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
                }
            };
    private final Map<String, Semester> semesters =
            new HashMap<String, Semester>() {
                {
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
                }
            };
    private final Map<String, StudentEntity> students =
            new HashMap<String, StudentEntity>() {
                {
                    put("Student1", new StudentEntity("s1127157", "Emma Dowd",
                            "emma.dowd@gmail.com",
                            new UserAccountEntity("student", "pass",
                                    Role.STUDENT)));
                    put("Student2",
                            new StudentEntity("s1123960", "Caroline Black",
                                    "caroline.black@uis.at", null));
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

    private void addTags() {
        courses.get("Course1")
                .addTags(tags.get("Tag1"), tags.get("Tag2"), tags.get("Tag3"));
        courses.get("Course2").addTags(tags.get("Tag1"), tags.get("Tag2"));
        courses.get("Course3").addTags(tags.get("Tag1"));
    }

    private void addCoursesToStudents() {
        asList(courses.get("Course1"), courses.get("Course2"),
                courses.get("Course3"))
                .forEach(it -> it.addStudents(students.get("Student2")));
    }

    @Before
    public void setUp() throws Exception {
        tagRepository.save(tags.values());
        subjectRepository.save(subjects.values());
        semesterRepository.save(semesters.values());
        courseRepository.save(courses.values());
        stduentRepository.save(students.values());

        addTags();
        addCoursesToStudents();
    }

    @Test
    public void itReturnsEmptyTagsFrequencyForStudentWithoutCourses()
            throws Exception {
        List<TagFrequency> tagsFrequency = stduentRepository
                .computeTagsFrequencyFor(students.get("Student1"));

        assertThat(tagsFrequency, empty());
    }

    @Test
    public void itReturnsTagsFrequencyForStudentWithCourses() throws Exception {
        List<TagFrequency> tagsFrequency = stduentRepository
                .computeTagsFrequencyFor(students.get("Student2"));

        Map<String, Long> actual = new HashMap<>();
        tagsFrequency.forEach(
                it -> actual.put(it.getTag().getName(), it.getFrequency()));

        Map<String, Long> expected = new HashMap<>();
        expected.put("Tag1", 3L);
        expected.put("Tag2", 2L);
        expected.put("Tag3", 1L);

        assertEquals(expected, actual);
    }

    @Test
    public void itReturnsNullOnFindByUsernameWithUnknownUsername()
            throws Exception {
        assertNull(stduentRepository.findByUsername("unknown"));
    }

    @Test
    public void itFindsStudentByUsername() throws Exception {
        assertEquals(students.get("Student1"),
                stduentRepository.findByUsername("student"));
    }
}
