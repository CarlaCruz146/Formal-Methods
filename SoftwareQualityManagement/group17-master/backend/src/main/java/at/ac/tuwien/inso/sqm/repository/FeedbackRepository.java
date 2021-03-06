package at.ac.tuwien.inso.sqm.repository;

import at.ac.tuwien.inso.sqm.entity.Feedback;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Long> {

    @Query("select f " + "from Feedback f " + "where f.student = ?1")
    List<Feedback> findAllOfStudent(StudentEntity student);

    @Query("select case when count(f) > 0 then true else false end " +
            "from Feedback f " +
            "where f.course = :#{#feedback.course} and f.student = " +
            ":#{#feedback.student}")
    boolean exists(@Param("feedback") Feedback feedback);

    List<Feedback> findByCourseId(Long id);
}
