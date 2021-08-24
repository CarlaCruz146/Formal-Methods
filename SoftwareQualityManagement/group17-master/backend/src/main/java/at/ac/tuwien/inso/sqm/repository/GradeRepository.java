package at.ac.tuwien.inso.sqm.repository;

import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GradeRepository extends CrudRepository<Grade, Long> {

    List<Grade> findByStudentAccountId(Long id);

    @Query("select g " + "from Grade g " + "where g.student = ?1")
    List<Grade> findAllOfStudent(StudentEntity student);

    List<Grade> findByCourseId(Long courseId);

    List<Grade> findByLecturerIdAndCourseId(Long lecturerId, Long courseId);

    Grade findByUrlIdentifier(String urlIdentifier);
}
