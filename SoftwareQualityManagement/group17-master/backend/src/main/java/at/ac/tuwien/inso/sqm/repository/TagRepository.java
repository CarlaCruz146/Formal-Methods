package at.ac.tuwien.inso.sqm.repository;

import at.ac.tuwien.inso.sqm.entity.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    @Deprecated
    List<Tag> findAll();

    List<Tag> findByValidTrue();

    Tag findByName(String name);
}
