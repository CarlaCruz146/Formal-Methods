package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.dto.AddCourseForm;
import at.ac.tuwien.inso.sqm.dto.CourseDetailsForStudent;
import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.SubjectForStudyPlanEntity;
import at.ac.tuwien.inso.sqm.exception.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface LehrveranstaltungServiceInterface {

    /**
     * this method returns all courses for the current semester with the
     * specified name of a subject
     * may start a new semester!
     * the user needs to be authenticated
     *
     * @param name     the name of a subject to search for, search strategy
     *                 is NameLikeIgnoreCase
     * @param pageable a spring pageable element
     * @return all courses for the current semester with the specified name of a subject
     */
    @PreAuthorize("isAuthenticated()")
    Page<Course> findCourseForCurrentSemesterWithName(
            @NotNull String name, Pageable pageable);

    /**
     * returns all courses of the given lecturer for the current semester
     * may start a new semester!
     * the user needs to be authenticated
     *
     * @param lecturer the lecturer
     * @return returns all courses of the given lecturer for the current semester
     */
    @PreAuthorize("isAuthenticated()")
    List<Course> findCoursesForCurrentSemesterForLecturer(
            LecturerEntity lecturer);

    /**
     * this method saves a new course by the given AddCourseForm
     * this method should also take care of tags that are contained by the
     * form. if they are new and have not been in the system before, they
     * should be created
     * the user needs to be lecturer or admin
     *
     * @param form the form
     * @return the created course
     */
    @PreAuthorize("hasAnyRole('ROLE_LECTURER', 'ROLE_ADMIN')")
    Course saveCourse(AddCourseForm form);

    /**
     * Diese Methode gibt eine Lehrveranstaltung anhand der übergebenen id
     * zurück.
     * Kann eine BusinessObjectNotFoundException werfen, wenn es keinen Kurs
     * mit der angegebenen id gibt.
     *
     * @param id Die id sollte nicht null sein und nicht <1
     * @return the course
     */
    @PreAuthorize("isAuthenticated()")
    Course findeLehrveranstaltung(Long id);

    /**
     * this method removes a course by its id if there are not registered
     * students or grades for integrationtest.
     *
     * @param id should not be null and not <1
     * @return if successfully removed
     * @throws ValidationException             if there are grades or already
     * registered students for that course
     */
    @PreAuthorize("hasAnyRole('ROLE_LECTURER', 'ROLE_ADMIN')")
    boolean remove(Long id) throws ValidationException;

    /**
     * Meldet den aktuell eingeloggten Studenten zu einer Lehrveranstaltung an.
     * Gibt false zurück, wenn der Kurs nicht genügend freie Kapazitäten hat,
     * ansonsten wird true zurück gegeben.
     * <p>
     * Der Benutzer muss von Rolle Student haben.
     *
     * @param course sollte eine id haben, die nicht null ist und
     *                          nicht <1
     * @return false if not enough capacity, otherwise true
     */
    @PreAuthorize("hasRole('STUDENT')")
    boolean studentZurLehrveranstaltungAnmelden(
            Course course);

    /**
     * returns a list with all courses for a student.
     * <p>
     * the user needs to be authenticated
     *
     * @param student the student
     * @return returns a list with all courses for a student
     */
    @PreAuthorize("isAuthenticated()")
    List<Course> findAllForStudent(StudentEntity student);

    /**
     * returns a list of courses for a given subject.
     * user needs to be authenticated
     *
     * @param subject the subject
     * @return a list of courses for a given subject
     */
    @PreAuthorize("isAuthenticated()")
    List<Course> findCoursesForSubject(Subject subject);

    /**
     * returns a list of courses for a given subject and the current semester.
     * user needs to be authenticated
     *
     * @param subject the subject
     * @return  a list of courses for a given subject and the current semester.
     */
    @PreAuthorize("isAuthenticated()")
    List<Course> findCoursesForSubjectAndCurrentSemester(
            Subject subject);

    /**
     * dismisses a course for a student (used as feedback for machine learning).
     * the user should be of role student
     *
     * @param student  should not be null
     * @param courseId should not be null and not <1
     */
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    void dismissCourse(StudentEntity student, Long courseId);

    /**
     * Meldet einen Studenten von der Lehrveranstaltung ab.
     * Benutzer muss authentifiziert sein.
     * <p>
     * Kann eine BusinessObjectNotFoundException werfen, wenn die
     * Lehrveranstaltung nicht existiert.
     *
     * @param student              sollte nicht null sein
     * @param lehrveranstaltungsID sollte nicht null sein und nicht <1
     * @return die Lehrveranstaltung ohne den abgemeldeten Studenten
     */
    @PreAuthorize("isAuthenticated()")
    Course studentVonLehrveranstaltungAbmelden(StudentEntity student,
                                               Long lehrveranstaltungsID);

    /**
     * returns course details for a student for a course id.
     * may throw a BusinessObjectNotFoundException if the course does not exist
     * may throw a Validation Exception if the student is null
     * user needs to be authenticated
     *
     * @param student  should not be null
     * @param courseId should not be null and not <1
     * @return course details for a student for a course id.
     */
    @PreAuthorize("isAuthenticated()")
    CourseDetailsForStudent courseDetailsFor(StudentEntity student,
                                             Long courseId);

    /**
     * returns subjectsforstudyplans for the given course.
     * <p>
     * the user needs to be lecturer or admin
     *
     * @param course should not be null and not <1 (if, this method will
     *               throw a ValidationException)
     * @return subjectsforstudyplans for the given course.
     */
    @PreAuthorize("hasAnyRole('ROLE_LECTURER', 'ROLE_ADMIN')")
    List<SubjectForStudyPlanEntity> getSubjectForStudyPlanList(
            Course course);

    /**
     * returns all courses for the current semester.
     * may start a new semester.
     *
     * @return all courses for the current semester
     */
    @PreAuthorize("isAuthenticated()")
    List<Course> findAllCoursesForCurrentSemester();
}
