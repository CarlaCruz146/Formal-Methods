package at.ac.tuwien.inso.sqm.integrationtest;

import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.Role;
import at.ac.tuwien.inso.sqm.entity.Semester;
import at.ac.tuwien.inso.sqm.entity.SemesterTypeEnum;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.Tag;
import at.ac.tuwien.inso.sqm.entity.UserAccountEntity;
import at.ac.tuwien.inso.sqm.repository.CourseRepository;
import at.ac.tuwien.inso.sqm.repository.LecturerRepository;
import at.ac.tuwien.inso.sqm.repository.SemestreRepository;
import at.ac.tuwien.inso.sqm.repository.StduentRepository;
import at.ac.tuwien.inso.sqm.repository.SubjectRepository;
import at.ac.tuwien.inso.sqm.repository.TagRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public abstract class AbstractCoursesTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected CourseRepository courseRepository;

    @Autowired
    protected TagRepository tagRepository;
    @Autowired
    protected SubjectRepository subjectRepository;
    @Autowired
    protected StduentRepository stduentRepository;
    protected UserAccountEntity user1 =
            new UserAccountEntity("lecturer1", "pass", Role.LECTURER);
    protected LecturerEntity lecturer1 =
            new LecturerEntity("l0001", "LecturerEntity 1", "email1@uis.at",
                    user1);
    protected LecturerEntity lecturer2 =
            new LecturerEntity("l0002", "LecturerEntity 2", "email2@uis.at",
                    new UserAccountEntity("lecturer2", "pass", Role.LECTURER));
    protected LecturerEntity lecturer3 =
            new LecturerEntity("l0003", "LecturerEntity 3", "email3@uis.at",
                    new UserAccountEntity("lecturer3", "pass", Role.LECTURER));
    protected UserAccountEntity studentUserAccount =
            new UserAccountEntity("student", "pass", Role.STUDENT);
    protected StudentEntity student =
            new StudentEntity("s1234", "StudentEntity", "student@uis.at",
                    studentUserAccount);
    protected Semester ss2016 =
            new Semester(2016, SemesterTypeEnum.SummerSemester);
    protected Semester ws2016 =
            new Semester(2016, SemesterTypeEnum.WinterSemester);
    protected Semester ss2017 =
            new Semester(2017, SemesterTypeEnum.SummerSemester);
    protected Semester ws2017 =
            new Semester(2017, SemesterTypeEnum.WinterSemester);
    protected Semester ss2018 =
            new Semester(2018, SemesterTypeEnum.SummerSemester);
    protected Semester ss2019 =
            new Semester(2019, SemesterTypeEnum.SummerSemester);
    protected Semester ss2020 =
            new Semester(2020, SemesterTypeEnum.SummerSemester);
    protected Semester ss2021 =
            new Semester(2021, SemesterTypeEnum.SummerSemester);
    protected Subject calculus = new Subject("Calculus", new BigDecimal(3.0));
    protected Subject sepm = new Subject("SEPM", new BigDecimal(6.0));
    protected Subject ase =
            new Subject("ASE", new BigDecimal(6.0)).addLecturers(lecturer3);
    protected Course sepmSS2016 =
            new Course(sepm, ss2016);
    protected Course sepmSS2021 =
            new Course(sepm, ss2021);
    protected Course aseSS2021 = new Course(ase, ss2021);
    protected Course calculusSS2021 =
            new Course(calculus, ss2021);
    protected Tag tag1 = new Tag("Computer Science");
    protected Tag tag2 = new Tag("Math");
    protected Tag tag3 = new Tag("Fun");
    protected Tag tag4 = new Tag("Easy");
    protected Tag tag5 = new Tag("Difficult");
    protected List<Course> expectedCourses;
    protected List<Course> expectedCoursesForLecturer1;
    protected List<Course> expectedCoursesForLecturer2;
    protected List<Course> expectedCoursesForLecturer3;
    @Autowired
    private SemestreRepository semesterRepository;
    @Autowired
    private LecturerRepository lecturerRepository;

    @Before
    public void setUp() {
        student = stduentRepository.save(student);

        lecturerRepository.save(lecturer1);
        lecturerRepository.save(lecturer2);
        lecturerRepository.save(lecturer3);

        semesterRepository.save(ss2016);
        semesterRepository.save(ws2016);
        semesterRepository.save(ss2017);
        //        semesterRepository.save(ws2017);
        //        semesterRepository.save(ss2018);
        //        semesterRepository.save(ss2019);
        //        semesterRepository.save(ss2020);
        semesterRepository.save(ss2021);

        subjectRepository.save(calculus);
        calculus.addLecturers(lecturer3);
        subjectRepository.save(sepm);
        sepm.addLecturers(lecturer1);
        subjectRepository.save(ase);
        ase.addLecturers(lecturer1, lecturer2);

        calculusSS2021.addStudents(student);

        sepmSS2016 = courseRepository.save(sepmSS2016);
        sepmSS2021 = courseRepository.save(sepmSS2021);
        aseSS2021 = courseRepository.save(aseSS2021);
        calculusSS2021 = courseRepository.save(calculusSS2021);

        expectedCourses = Arrays.asList(sepmSS2021, aseSS2021, calculusSS2021);
        expectedCoursesForLecturer1 = Arrays.asList(sepmSS2021, aseSS2021);
        expectedCoursesForLecturer3 = Arrays.asList(aseSS2021);
        expectedCoursesForLecturer2 = Arrays.asList(calculusSS2021);

        tagRepository.save(asList(tag1, tag2, tag3, tag4, tag5));
    }


}
