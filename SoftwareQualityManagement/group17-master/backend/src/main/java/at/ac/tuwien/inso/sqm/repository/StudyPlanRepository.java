package at.ac.tuwien.inso.sqm.repository;

import at.ac.tuwien.inso.sqm.entity.StudyPlanEntity;
import org.springframework.data.repository.CrudRepository;

public interface StudyPlanRepository
        extends CrudRepository<StudyPlanEntity, Long> {
}
