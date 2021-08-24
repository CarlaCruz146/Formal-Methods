package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.dto.SemesterDto;
import at.ac.tuwien.inso.sqm.entity.StudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.StudyPlanRegistration;
import at.ac.tuwien.inso.sqm.entity.UserAccountEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface StudentServiceInterface {

    /**
     * returns one student by id if he exists.
     * id should not be null and not <1
     *
     * @param id the id
     * @return one student by id if he exists.
     */
    @PreAuthorize("isAuthenticated()")
    StudentEntity findOne(Long id);

    /**
     * returns one student by account.
     * user needs to be authenticated
     *
     * @param account the account
     * @return one student by account
     */
    @PreAuthorize("isAuthenticated()")
    StudentEntity findOne(UserAccountEntity account);

    /**
     * returns a student by its username.
     * user needs to be authenticated
     *
     * @param username the username
     * @return a student by its username
     */
    @PreAuthorize("isAuthenticated()")
    StudentEntity findByUsername(String username);

    /**
     * returns all studyplanregistrations for the student. student should not
     * be null!
     * user needs to be admin
     *
     * @param student the student
     * @return  all studyplanregistrations for the student
     */
    @PreAuthorize("hasRole('ADMIN')")
    List<StudyPlanRegistration> findStudyPlanRegistrationsFor(
            StudentEntity student);

    /**
     * registers a student to a stduyplan for the current semester. student
     * and stduyplan should not be null
     * may start a new semester
     * <p>
     * user needs to be admin
     *
     * @param student the student
     * @param studyPlan the study plan
     */
    @PreAuthorize("hasRole('ADMIN')")
    void registerStudentToStudyPlan(StudentEntity student,
            StudyPlanEntity studyPlan);

    /**
     * registers a student to a stduyplan for the given semester
     * student and stduyplan should not be null
     * <p>
     * user needs to be admin!
     *
     * @param student the student
     * @param studyPlan the study plan
     * @param currentSemester the current semester
     */
    @PreAuthorize("hasRole('ADMIN')")
    void registerStudentToStudyPlan(StudentEntity student,
                                    StudyPlanEntity studyPlan, SemesterDto currentSemester);
}
