package at.ac.tuwien.inso.sqm.service;

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
import at.ac.tuwien.inso.sqm.exception.BusinessObjectNotFoundException;
import at.ac.tuwien.inso.sqm.exception.ValidationException;
import at.ac.tuwien.inso.sqm.repository.GradeRepository;
import org.jboss.aerogear.security.otp.Totp;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
@Transactional
public class GradeServiceTest {

    private static final int TWO_FACTOR_AUTHENTICATION_TIMEOUT_SECONDS = 30;
    private static final Long VALID_STUDENT_ID = 1L;
    private static final Long VALID_UNREGISTERED_STUDENT_ID = 2L;
    private static final Long INVALID_STUDENT_ID = 1337L;
    private static final Long VALID_COURSE_ID = 3L;
    private static final Long INVALID_COURSE_ID = 337L;
    private final Semester ws2016 =
            new Semester(2016, SemesterTypeEnum.WinterSemester);
    private final Course course =
            new Course(new Subject("ASE", BigDecimal.ONE), ws2016);
    private final StudentEntity student =
            new StudentEntity("StudentEntity", "StudentEntity",
                    "StudentEntity@student.com");
    private final StudentEntity unregisteredStudent =
            new StudentEntity("Student2", "Student2", "not@registered.com");
    private final UserAccountEntity user =
            new UserAccountEntity("lecturer1", "pass", Role.LECTURER);
    private final LecturerEntity lecturer =
            new LecturerEntity("LecturerEntity", "LecturerEntity",
                    "lecturer@lecturer.com", user);
    private final Grade validGrade =
            new Grade(course, lecturer, student, MarkEntity.SATISFACTORY);
    private GradeIService gradeService;
    @Mock
    private LecturerService lecturerService;
    @Mock
    private StudentServiceInterface studentService;
    @Mock
    private LehrveranstaltungServiceInterface courseService;
    @Mock
    private UserAccountService userAccountService;
    @Mock
    private GradeRepository gradeRepository;

    @Before
    public void setUp() {

        course.getStudents().add(student);

        MockitoAnnotations.initMocks(this);
        when(courseService.findeLehrveranstaltung(VALID_COURSE_ID))
                .thenReturn(course);
        when(courseService.findeLehrveranstaltung(INVALID_COURSE_ID))
                .thenReturn(null);
        when(studentService.findOne(VALID_STUDENT_ID)).thenReturn(student);
        when(studentService.findOne(VALID_UNREGISTERED_STUDENT_ID))
                .thenReturn(unregisteredStudent);
        when(studentService.findOne(INVALID_STUDENT_ID)).thenReturn(null);
        when(lecturerService.getLoggedInLecturer()).thenReturn(lecturer);
        when(gradeRepository.save(validGrade)).thenReturn(validGrade);


        gradeService =
                new GradeService(gradeRepository, studentService, courseService,
                        lecturerService, userAccountService);
    }

    @Test
    @WithMockUser(roles = "Lecturer")
    public void getDefaultGradeForStudentAndCourseTest() {
        GradeAuthorizationDTO gradeAuth = gradeService
                .getDefaultGradeAuthorizationDTOForStudentAndCourse(
                        VALID_STUDENT_ID, VALID_COURSE_ID);
        Grade grade = gradeAuth.getGrade();
        assertEquals(grade.getCourse(), course);
        assertEquals(grade.getStudent(), student);
        assertEquals(grade.getLecturer(), lecturer);
        assertEquals(grade.getMark(), MarkEntity.FAILED);
        assertNull(grade.getId());
    }

    @Test(expected = BusinessObjectNotFoundException.class)
    @WithMockUser(roles = "Lecturer")
    public void getDefaultGradeForInvalidStudentAndCourseTest() {
        gradeService.getDefaultGradeAuthorizationDTOForStudentAndCourse(
                INVALID_STUDENT_ID, VALID_COURSE_ID);
    }


    @Test(expected = BusinessObjectNotFoundException.class)
    @WithMockUser(roles = "Lecturer")
    public void getDefaultGradeForStudentAndInvalidCourseTest() {
        gradeService.getDefaultGradeAuthorizationDTOForStudentAndCourse(
                VALID_STUDENT_ID, INVALID_COURSE_ID);
    }

    @Test(expected = BusinessObjectNotFoundException.class)
    @WithMockUser(roles = "Lecturer")
    public void getDefaultGradeForInvalidStudentAndInvalidCourseTest() {
        gradeService.getDefaultGradeAuthorizationDTOForStudentAndCourse(
                INVALID_STUDENT_ID, INVALID_COURSE_ID);
    }

    @Test(expected = ValidationException.class)
    @WithMockUser(roles = "Lecturer")
    public void getDefaultGradeForUnregisteredStudentAndCourseTest() {
        gradeService.getDefaultGradeAuthorizationDTOForStudentAndCourse(
                VALID_UNREGISTERED_STUDENT_ID, VALID_COURSE_ID);
    }

    @Test
    @WithUserDetails("lecturer1")
    public void saveNewGradeForStudentAndCourseTest() {
        Totp totp = new Totp(lecturer.getTwoFactorSecret());
        Grade result = gradeService.saveNewGradeForStudentAndCourse(
                new GradeAuthorizationDTO(validGrade, totp.now()));
        assertEquals(validGrade.getLecturer(), result.getLecturer());
        assertEquals(validGrade.getStudent(), result.getStudent());
        assertEquals(validGrade.getCourse(), result.getCourse());
        assertEquals(validGrade.getMark(), result.getMark());
    }

    @Ignore //Takes more than a minute
    @Test(expected = ValidationException.class)
    @WithUserDetails("lecturer1")
    public void saveNewGradeForStudentAndCourseTestTwoFactorAuthFail()
            throws InterruptedException {
        Totp totp = new Totp(lecturer.getTwoFactorSecret());
        String code = totp.now();
        Thread.sleep(2 * TWO_FACTOR_AUTHENTICATION_TIMEOUT_SECONDS * 1000);
        gradeService.saveNewGradeForStudentAndCourse(
                new GradeAuthorizationDTO(validGrade, code));
    }

    @Test(expected = ValidationException.class)
    @WithUserDetails("lecturer1")
    public void saveNewGradeForStudentAndCourseTestTwoFactorAuthFailWrongCode()
            throws InterruptedException {
        Totp totp = new Totp(lecturer.getTwoFactorSecret());
        String code = "123456";
        gradeService.saveNewGradeForStudentAndCourse(
                new GradeAuthorizationDTO(validGrade, code));
    }

}
