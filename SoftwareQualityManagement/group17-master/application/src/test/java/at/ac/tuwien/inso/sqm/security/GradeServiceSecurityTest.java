package at.ac.tuwien.inso.sqm.security;

import at.ac.tuwien.inso.sqm.dto.GradeAuthorizationDTO;
import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.MarkEntity;
import at.ac.tuwien.inso.sqm.entity.Role;
import at.ac.tuwien.inso.sqm.entity.Semester;
import at.ac.tuwien.inso.sqm.entity.SemesterTypeEnum;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.UserAccountEntity;
import at.ac.tuwien.inso.sqm.exception.ValidationException;
import at.ac.tuwien.inso.sqm.repository.CourseRepository;
import at.ac.tuwien.inso.sqm.repository.LecturerRepository;
import at.ac.tuwien.inso.sqm.repository.SemestreRepository;
import at.ac.tuwien.inso.sqm.repository.StduentRepository;
import at.ac.tuwien.inso.sqm.repository.SubjectRepository;
import at.ac.tuwien.inso.sqm.service.GradeIService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class GradeServiceSecurityTest {

    private final UserAccountEntity user =
            new UserAccountEntity("lecturer1", "pass", Role.LECTURER);
    @Autowired
    private GradeIService gradeService;
    @Autowired
    private LecturerRepository lecturerRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private SemestreRepository semesterRepository;
    @Autowired
    private StduentRepository stduentRepository;
    private LecturerEntity lecturer;
    private Course course;
    private StudentEntity student;

    @BeforeTransaction
    public void beforeTransaction() {
        lecturer = lecturerRepository
                .save(new LecturerEntity("2", "LecturerEntity",
                        "lecturer@lecturer.com", user));
    }

    @AfterTransaction
    public void afterTransaction() {
        lecturerRepository.delete(lecturer);
    }

    @Before
    public void setUp() {
        student = stduentRepository
                .save(new StudentEntity("1", "student1", "s@student.com"));
        Subject subject = subjectRepository
                .save(new Subject("ASE", BigDecimal.valueOf(6)));
        subject.addLecturers(lecturer);
        subjectRepository.save(subject);
        Semester semester = semesterRepository
                .save(new Semester(2016, SemesterTypeEnum.WinterSemester));
        course =
                courseRepository.save(new Course(subject, semester));
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getDefaultGradeForStudentAndCourseNotAuthenticated() {
        gradeService.getDefaultGradeAuthorizationDTOForStudentAndCourse(
                student.getId(), course.getId());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "STUDENT")
    public void getDefaultGradeForStudentAndCourseAuthenticatedAsStudent() {
        gradeService.getDefaultGradeAuthorizationDTOForStudentAndCourse(
                student.getId(), course.getId());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "ADMIN")
    public void getDefaultGradeForStudentAndCourseAuthenticatedAsAdmin() {
        gradeService.getDefaultGradeAuthorizationDTOForStudentAndCourse(
                student.getId(), course.getId());
    }

    @Test(expected = ValidationException.class)
    @WithUserDetails("lecturer1")
    public void getDefaultGradeForStudentAndCourseAuthenticatedAsLecturer() {
        gradeService.getDefaultGradeAuthorizationDTOForStudentAndCourse(
                student.getId(), course.getId());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void saveNewGradeForStudentAndCourseNotAuthenticated() {
        gradeService.saveNewGradeForStudentAndCourse(new GradeAuthorizationDTO(
                new Grade(course, lecturer, student, MarkEntity.EXCELLENT)));
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "STUDENT")
    public void saveNewGradeForStudentAndCourseAuthenticatedAsStudent() {
        gradeService.saveNewGradeForStudentAndCourse(new GradeAuthorizationDTO(
                new Grade(course, lecturer, student, MarkEntity.EXCELLENT)));
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "ADMIN")
    public void saveNewGradeForStudentAndCourseAuthenticatedAsAdmin() {
        gradeService.saveNewGradeForStudentAndCourse(new GradeAuthorizationDTO(
                new Grade(course, lecturer, student, MarkEntity.EXCELLENT)));
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getGradesForLoggedInStudentNotAuthenticated() {
        gradeService.getGradesForLoggedInStudent();
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "ADMIN")
    public void getGradesForLoggedInStudentAsAdmin() {
        gradeService.getGradesForLoggedInStudent();
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "LECTURER")
    public void getGradesForLoggedInStudentAsLecturer() {
        gradeService.getGradesForLoggedInStudent();
    }

    @Test(expected = UsernameNotFoundException.class)
    @WithMockUser(roles = "STUDENT")
    public void getGradesForLoggedInStudentAsStudent() {
        gradeService.getGradesForLoggedInStudent();
    }

    @Test(expected = Test.None.class) // make explicit that is should not throw an Exception
    public void getForValidationTestNotAuthenticated() {
        gradeService.getForValidation("1");
    }


}
