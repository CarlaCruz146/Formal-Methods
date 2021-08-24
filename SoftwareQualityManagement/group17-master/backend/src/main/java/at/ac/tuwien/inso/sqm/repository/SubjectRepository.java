package at.ac.tuwien.inso.sqm.repository;

import at.ac.tuwien.inso.sqm.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, Long> {

    List<Subject> findByLecturersId(Long id);

    Subject findById(Long id);

    List<Subject> findByNameContainingIgnoreCase(String name);

    Page<Subject> findAll(Pageable pageable);

    Page<Subject> findAllByNameLikeIgnoreCase(String name, Pageable pageable);
}
