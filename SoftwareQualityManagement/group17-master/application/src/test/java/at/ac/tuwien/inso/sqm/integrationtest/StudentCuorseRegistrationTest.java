package at.ac.tuwien.inso.sqm.integrationtest;

import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.MarkEntity;
import at.ac.tuwien.inso.sqm.entity.Role;
import at.ac.tuwien.inso.sqm.entity.Semester;
import at.ac.tuwien.inso.sqm.entity.SemesterTypeEnum;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.Tag;
import at.ac.tuwien.inso.sqm.entity.UserAccountEntity;
import at.ac.tuwien.inso.sqm.repository.CourseRepository;
import at.ac.tuwien.inso.sqm.repository.GradeRepository;
import at.ac.tuwien.inso.sqm.repository.LecturerRepository;
import at.ac.tuwien.inso.sqm.repository.SemestreRepository;
import at.ac.tuwien.inso.sqm.repository.StduentRepository;
import at.ac.tuwien.inso.sqm.repository.SubjectRepository;
import at.ac.tuwien.inso.sqm.repository.TagRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Ignore
public class StudentCuorseRegistrationTest extends AbstractCoursesTest {

    UserAccountEntity user1 =
            new UserAccountEntity("lecturer1", "pass", Role.LECTURER);
    UserAccountEntity studentUser =
            new UserAccountEntity("student1", "pass", Role.STUDENT);
    UserAccountEntity student2User =
            new UserAccountEntity("student2", "pass", Role.STUDENT);
    LecturerEntity lecturer1 =
            new LecturerEntity("l0001", "LecturerEntity 1", "email1@uis.at",
                    user1);
    LecturerEntity lecturer2 =
            new LecturerEntity("l0002", "LecturerEntity 2", "email2@uis.at",
                    new UserAccountEntity("lecturer2", "pass", Role.LECTURER));
    LecturerEntity lecturer3 =
            new LecturerEntity("l0003", "LecturerEntity 3", "email3@uis.at",
                    new UserAccountEntity("lecturer3", "pass", Role.LECTURER));
    StudentEntity student1 =
            new StudentEntity("s000001", "student1", "email11@uis.at",
                    studentUser);
    StudentEntity student2 =
            new StudentEntity("s000002", "student2", "email12@uis.at",
                    student2User);
    StudentEntity student3 =
            new StudentEntity("s000003", "student3", "email13@uis.at",
                    new UserAccountEntity("student3", "pass", Role.STUDENT));
    Semester ss2016 = new Semester(2016, SemesterTypeEnum.SummerSemester);
    Semester ws2016 = new Semester(2016, SemesterTypeEnum.WinterSemester);
    Subject calculus = new Subject("Calculus", new BigDecimal(3.0));
    Subject sepm = new Subject("SEPM", new BigDecimal(6.0));
    Subject ase = new Subject("ASE", new BigDecimal(6.0));
    Course sepmSS2016 = new Course(sepm, ss2016);
    Course sepmWS2016 = new Course(sepm, ws2016);
    Course aseWS2016 = new Course(ase, ws2016);
    Course calculusWS2016 = new Course(calculus, ws2016);
    List<Grade> grades = new ArrayList<>();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SemestreRepository semesterRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private LecturerRepository lecturerRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private StduentRepository stduentRepository;
    @Autowired
    private GradeRepository gradeRepository;


    @Before
    public void setUp() {

        lecturerRepository.save(lecturer1);
        lecturerRepository.save(lecturer2);
        lecturerRepository.save(lecturer3);

        stduentRepository.save(asList(student1, student2, student3));

        semesterRepository.save(ss2016);
        semesterRepository.save(ws2016);

        subjectRepository.save(calculus);
        calculus.addLecturers(lecturer3);
        subjectRepository.save(sepm);
        sepm.addLecturers(lecturer1);
        subjectRepository.save(ase);
        ase.addLecturers(lecturer1, lecturer2);

        sepmSS2016 = courseRepository.save(sepmSS2016);
        sepmWS2016 = courseRepository.save(sepmWS2016);
        aseWS2016.setStudentLimits(10);
        aseWS2016.addStudents(student2);
        aseWS2016 = courseRepository.save(aseWS2016);
        calculusWS2016 = courseRepository.save(calculusWS2016);

        Grade grade =
                new Grade(aseWS2016, lecturer1, student2, MarkEntity.EXCELLENT);
        grade = gradeRepository.save(grade);
        grades.add(grade);

        tagRepository.save(asList(new Tag("Computer Science"), new Tag("Math"),
                new Tag("Fun"), new Tag("Easy"), new Tag("Difficult")));
    }

    //    @Test
    //    public void itDoesNotRegisterStudent() throws Exception {
    //
    //        mockMvc.perform(
    //                post("/student/anmelden/" + sepmWS2016.getId()).with
    //                (user(studentUser))
    //                        .with(csrf())
    //        ).andExpect(
    //                redirectedUrl("/student/lehrveranstaltungen")
    //        );
    //
    //        assertFalse(aseWS2016.getStudents().contains(student1));
    //    }
    //
    //    @Test
    //    public void itRegistersStudent() throws Exception {
    //
    //        mockMvc.perform(
    //                post("/student/anmelden/" + aseWS2016.getId()).with
    //                (user(studentUser))
    //                        .with(csrf())
    //        ).andExpect(
    //                redirectedUrl("/student/lehrveranstaltungen")
    //        );
    //
    //        assertTrue(aseWS2016.getStudents().contains(student1));
    //    }
    //
    //    @Test
    //    public void
    //    itUnregistersStudentFromCourseAndRedirectsToMyCoursesPage() throws
    //    Exception {
    //
    //        mockMvc.perform(
    //                post("/student/abmelden")
    //                        .with(csrf())
    //                        .param("course", aseWS2016.getId().toString())
    //                        //.with(user(student2User))
    //                        .with(user("student2").roles(Rolle.STUDENT.name
    //                        ()))
    //        ).andExpect(result ->
    //                assertThat(courseRepository.findOne(aseWS2016.getId())
    //                .getStudents(), empty())
    //        ).andExpect(
    //                redirectedUrl("/student/meineLehrveranstaltungen")
    //        );
    //
    //        assertFalse(aseWS2016.getStudents().contains(student2));
    //    }
}
