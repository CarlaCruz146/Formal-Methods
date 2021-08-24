package at.ac.tuwien.inso.sqm.repository;

import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LecturerRepository
        extends CrudRepository<LecturerEntity, Long> {

    @Query("select l from LecturerEntity l where ACCOUNT_ID = ?1")
    LecturerEntity findLecturerByAccountId(Long id);


    List<LecturerEntity> findAllByIdentificationNumberLikeIgnoreCaseOrNameLikeIgnoreCase(
            String identificationNumber, String name);

    LecturerEntity findById(Long id);
}
