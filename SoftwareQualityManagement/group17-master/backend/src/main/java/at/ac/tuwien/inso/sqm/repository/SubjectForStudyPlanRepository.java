package at.ac.tuwien.inso.sqm.repository;

import at.ac.tuwien.inso.sqm.entity.StudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.SubjectForStudyPlanEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectForStudyPlanRepository
        extends CrudRepository<SubjectForStudyPlanEntity, Long> {

    List<SubjectForStudyPlanEntity> findByStudyPlanIdOrderBySemesterRecommendation(
            Long id);

    List<SubjectForStudyPlanEntity> findBySubject(Subject subject);

    List<SubjectForStudyPlanEntity> findBySubjectInAndStudyPlan(
            List<Subject> subjects, StudyPlanEntity studyPlan);
}
