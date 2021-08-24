package at.ac.tuwien.inso.sqm.repository;

import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.UserAccountEntity;
import at.ac.tuwien.inso.sqm.repository.utils.TagFrequency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StduentRepository extends CrudRepository<StudentEntity, Long> {

    @Query("select new at.ac.tuwien.inso.sqm.repository.utils.TagFrequency(t," +
            " count(t)) " +
            "from Course c join c.tags t " +
            "where ?1 member of c.students " + "group by t")
    List<TagFrequency> computeTagsFrequencyFor(StudentEntity student);

    StudentEntity findByAccount(UserAccountEntity account);

    @Query("select s " + "from StudentEntity s join s.account a " +
            "where a.username = ?1")
    StudentEntity findByUsername(String username);
}
