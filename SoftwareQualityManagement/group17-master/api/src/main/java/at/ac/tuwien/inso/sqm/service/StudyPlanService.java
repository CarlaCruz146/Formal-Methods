package at.ac.tuwien.inso.sqm.service;


import at.ac.tuwien.inso.sqm.entity.StudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.SubjectForStudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.SubjectWithGrade;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface StudyPlanService {

    /**
     * creates a new study plan.
     * may throw a ValidationException if study plans name, or optional,
     * mandatory or freechoice ects values are null or empty or <=0
     *
     * @param studyPlan the study plan to be created
     * @return the created study plan
     */
    @PreAuthorize("hasRole('ADMIN')")
    StudyPlanEntity create(StudyPlanEntity studyPlan);

    /**
     * returns a list of all StudyPlans.
     * user must be authorized
     *
     * @return a list of all StudyPlans
     */
    @PreAuthorize("isAuthenticated()")
    List<StudyPlanEntity> findAll();

    /**
     * returns the StduyPlanEntity with the corresponding id.
     * may throw a BusinessObjectNotFoundException if there is no
     * StduyPlanEntity with this id
     *
     * @param id should not be null and not <1
     * @return the StudyPlanEntity with the corresponding id.
     */
    @PreAuthorize("isAuthenticated()")
    StudyPlanEntity findOne(Long id);

    /**
     * try to find all SubjectsForStudyPlan by a study plan id.
     * should be ordered by semester recommendation
     * user must be authorized
     *
     * @param id should not be null and not <1
     * @return all SubjectsForStudyPlan by a study plan id.
     */
    @PreAuthorize("isAuthenticated()")
    List<SubjectForStudyPlanEntity> getSubjectsForStudyPlan(Long id);

    /**
     * returns a list of grades for the subjects for the CURRENTLY LOGGED IN
     * STUDENT.
     * user needs to be authenticated
     *
     * @param id should not be null and not <1
     * @return a list of grades for the subjects for the CURRENTLY LOGGED IN STUDENT.
     */
    @PreAuthorize("hasRole('STUDENT')")
    List<SubjectWithGrade> getSubjectsWithGradesForStudyPlan(Long id);

    /**
     * adds a subject to a study plan.
     * user needs to be ADMIN
     *
     * @param subjectForStudyPlan should contain a subject that is not null
     *                            and has a id that is not <1. also should
     *                            contain a study plan that is not null and
     *                            has an id that is not null and not <1
     */
    @PreAuthorize("hasRole('ADMIN')")
    void addSubjectToStudyPlan(SubjectForStudyPlanEntity subjectForStudyPlan);

    /**
     * returns all available subjects for the study plan with the id. the
     * subjects can be filtered with the query string
     * the search strategy of the query should be byNameContainingIgnoreCase
     * (query)
     * <p>
     * user has to be authenticated
     *
     * @param id    should not be null and not <1
     * @param query the query
     * @return ll available subjects for the study plan with the id filtered with the query string
     */
    @PreAuthorize("isAuthenticated()")
    List<Subject> getAvailableSubjectsForStudyPlan(Long id, String query);

    /**
     * disables the study plan of the given id.
     * <p>
     * user needs role ADMIN
     * may throw BusinessObjectNotFoundException if the study plan with this
     * id does not exists
     * may throw a ValidationException if the id is not correct
     *
     * @param id should not be null and not <1
     * @return the study plan entity which is disabled
     */
    @PreAuthorize("hasRole('ADMIN')")
    StudyPlanEntity disableStudyPlan(Long id);

    /**
     * removes a given subject s from the study plan sp.
     * user need role ADMIN
     *
     * @param sp should not be null and the sp.id should not be <1 and not null
     * @param s  should have an id
     */
    @PreAuthorize("hasRole('ADMIN')")
    void removeSubjectFromStudyPlan(StudyPlanEntity sp, Subject s);
}
