package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.dto.GradeAuthorizationDTO;
import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.entity.MarkEntity;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GradeIService {

    /**
     * returns a default GradeAuthroizationDto. Default means that the grade
     * is failed = MarkEntity.FAILED.
     * if no logged in lecturer, no student with the id, no course can be
     * found, a BusinessObjectNotFoundException will be thrown
     * if student is not registered for the course, a ValidationException
     * will be thrown
     *
     * @param studentId the student id
     * @param courseId the course id
     * @return a default GradeAuthorizationDto
     */
    @PreAuthorize("hasRole('LECTURER')")
    GradeAuthorizationDTO getDefaultGradeAuthorizationDTOForStudentAndCourse(
            Long studentId, Long courseId);


    /**
     * saves a new grade for student for a course.
     * <p>
     * the lecturer of the gradeauthrizationDTO needs to be equal to the
     * currently logged in lecturer. otherwise a ValidationException is thrown
     * further, lecturer needs to have a two factor authentication value set.
     * otherwise a ValidationException will be thrown
     * the authentication code value needs to be a number. otherwise a
     * ValidationException is thrown
     *
     * @param grade the grade
     * @return the saved grade
     */
    @PreAuthorize("hasRole('LECTURER')")
    Grade saveNewGradeForStudentAndCourse(GradeAuthorizationDTO grade);

    /**
     * returns a list of grades for the given course  of the currently logged.
     * in lecturer
     * <p>
     * user needs to be lecturer
     *
     * @param courseId the course id
     * @return  list of grades for the given course  of the currently logged
     */
    @PreAuthorize("hasRole('LECTURER')")
    List<Grade> getGradesForCourseOfLoggedInLecturer(Long courseId);

    /**
     * returns a list of grades for the given courseID.
     * user needs to be lecturer
     *
     * @param courseId should not be null and not <1
     * @return a list of grades for the given courseID
     */
    @PreAuthorize("hasRole('LECTURER')")
    List<Grade> findAllByCourseId(Long courseId);

    /**
     * returns all grades of the currently logged in student.
     * <p>
     * user needs to be student
     *
     * @return all grades of the currently logged in student
     */
    @PreAuthorize("hasRole('STUDENT')")
    List<Grade> getGradesForLoggedInStudent();

    /**
     * returns the grade for a string identifier for validation purposes.
     * <p>
     * user needs not authentication
     *
     * @param identifier the identifier
     * @return the grade for a string identifier for validation purposes
     */
    Grade getForValidation(String identifier);

    /**
     * returns all grades for a given student.
     *
     * @param student should not be null
     * @return all grades for a given student
     */
    @PreAuthorize("isAuthenticated()")
    List<Grade> findAllOfStudent(StudentEntity student);

    /**
     * returns a list of MarkEntity options (MarkEntity is an enumeration).
     *
     * @return a list of MarkEntity options
     */
    @PreAuthorize("isAuthenticated()")
    List<MarkEntity> getMarkOptions();

}
