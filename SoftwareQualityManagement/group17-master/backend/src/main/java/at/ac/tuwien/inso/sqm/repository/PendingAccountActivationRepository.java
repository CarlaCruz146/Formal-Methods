package at.ac.tuwien.inso.sqm.repository;

import at.ac.tuwien.inso.sqm.entity.PendingAcountActivation;
import org.springframework.data.repository.CrudRepository;

public interface PendingAccountActivationRepository
        extends CrudRepository<PendingAcountActivation, String> {
}
