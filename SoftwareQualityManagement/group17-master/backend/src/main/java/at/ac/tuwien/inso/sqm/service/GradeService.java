package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.dto.GradeAuthorizationDTO;
import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.MarkEntity;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.exception.BusinessObjectNotFoundException;
import at.ac.tuwien.inso.sqm.exception.ValidationException;
import at.ac.tuwien.inso.sqm.repository.GradeRepository;
import org.jboss.aerogear.security.otp.Totp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class GradeService implements GradeIService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(GradeService.class);

    private final GradeRepository gradeRepository;
    private final StudentServiceInterface studentService;
    private final LehrveranstaltungServiceInterface courseService;
    private final LecturerService lecturerService;
    private final UserAccountService userAccountService;

    @Autowired
    public GradeService(GradeRepository gradeRepository,
            StudentServiceInterface studentService,
            LehrveranstaltungServiceInterface courseService,
            LecturerService lecturerService,
            UserAccountService userAccountService) {
        this.gradeRepository = gradeRepository;
        this.studentService = studentService;
        this.courseService = courseService;
        this.lecturerService = lecturerService;
        this.userAccountService = userAccountService;
    }

    @Override
    public GradeAuthorizationDTO getDefaultGradeAuthorizationDTOForStudentAndCourse(
            Long studentId, Long courseId) {
        LOGGER.info(
                "getting default grade authorization dto for student with id " +
                        studentId + " and course with id " + courseId);
        StudentEntity student = studentService.findOne(studentId);
        LecturerEntity lecturer = lecturerService.getLoggedInLecturer();
        Course course =
                courseService.findeLehrveranstaltung(courseId);
        if (course == null || lecturer == null || student == null) {
            LOGGER.warn("Wrong student or course id");
            throw new BusinessObjectNotFoundException(
                    "Wrong student or course id");
        }
        if (!course.getStudents().contains(student)) {
            LOGGER.warn("student not registered for course");
            throw new ValidationException(
                    "StudentEntity not registered for course!");
        }
        return new GradeAuthorizationDTO(
                new Grade(course, lecturer, student, MarkEntity.FAILED));
    }

    @Override
    public Grade saveNewGradeForStudentAndCourse(
            GradeAuthorizationDTO gradeAuthorizationDTO) {
        LOGGER.info("saving new grade for student and course");
        Grade grade = gradeAuthorizationDTO.getGrade();
        if (!grade.getLecturer()
                .equals(lecturerService.getLoggedInLecturer())) {
            LOGGER.warn("LecturerEntity is not valid");
            throw new ValidationException("LecturerEntity is not valid!");
        }
        String oneTimePassword = gradeAuthorizationDTO.getAuthCode();
        Totp authenticator = new Totp(grade.getLecturer().getTwoFactorSecret());
        try {
            if (!authenticator.verify(oneTimePassword)) {
                LOGGER.info("Auth-code is not valid");
                throw new ValidationException("Auth-code is not valid!");
            }
            return gradeRepository.save(grade);
        } catch (NumberFormatException e) {
            LOGGER.info("Auth-code cannot be cast to a number, value is: [{}]",
                    oneTimePassword);
            throw new ValidationException("Auth-code is not a number");
        }
    }

    @Override
    public List<Grade> getGradesForCourseOfLoggedInLecturer(Long courseId) {
        LecturerEntity lecturer = lecturerService.getLoggedInLecturer();
        LOGGER.info(
                "getting grades for couse of logged in lecturer with courseid" +
                        courseId + " and lecturerid " + lecturer.getId());
        return gradeRepository
                .findByLecturerIdAndCourseId(lecturer.getId(), courseId);
    }

    @Override
    public List<Grade> getGradesForLoggedInStudent() {
        Long studentId = userAccountService.getCurrentLoggedInUser().getId();
        LOGGER.info("getting grades for logged in student with id " + studentId);
        return gradeRepository.findByStudentAccountId(studentId);
    }

    @Override
    public Grade getForValidation(String identifier) {
        LOGGER.info("getting validation for identifier " + identifier);
        return gradeRepository.findByUrlIdentifier(identifier);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Grade> findAllOfStudent(StudentEntity student) {
        LOGGER.info("finding all grades for student with id " + student.getId());
        return gradeRepository.findAllOfStudent(student);
    }

    @Override
    public List<MarkEntity> getMarkOptions() {
        //return Arrays.asList(MarkEntity.EXCELLENT, MarkEntity.GOOD,
        // MarkEntity.SATISFACTORY, MarkEntity.SUFFICIENT, MarkEntity.FAILED);
        return Collections.unmodifiableList(
                Arrays.asList(MarkEntity.EXCELLENT, MarkEntity.GOOD,
                        MarkEntity.SATISFACTORY, MarkEntity.SUFFICIENT,
                        MarkEntity.FAILED));
    }

    @Override
    public List<Grade> findAllByCourseId(Long courseId) {
        LOGGER.info("trying to find all courses by id " + courseId);
        return gradeRepository.findByCourseId(courseId);
    }

    private Long parseValidationIdentifier(String identifier) {
        return Long.valueOf(identifier);
    }
}
