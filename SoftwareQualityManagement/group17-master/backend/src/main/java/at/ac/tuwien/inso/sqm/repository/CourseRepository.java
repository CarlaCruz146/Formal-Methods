package at.ac.tuwien.inso.sqm.repository;

import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.Semester;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository
        extends CrudRepository<Course, Long> {

    Page<Course> findAllBySemesterAndSubjectNameLikeIgnoreCase(
            Semester semester, String name, Pageable pageable);

    List<Course> findAllBySemesterAndSubject(Semester semester,
                                             Subject subject);

    List<Course> findAllBySubject(Subject subject);

    @Query("select c " + "from Course c " + "where c.semester = (" +
            "   select s " + "   from Semester s " + "   where s.id = ( " +
            "       select max(s1.id) " + "       from Semester s1 " +
            "       )" + "   ) " + "and :student not member of c.students " +
            "and c.subject not in (" + "   select g.course.subject " +
            "   from Grade g " +
            "   where g.student = :student and g.mark.mark <> 5" + ") " +
            "and c not in :#{#student.dismissedCourses}")
    List<Course> findAllRecommendableForStudent(
            @Param("student") StudentEntity student);

    @Query("select c " + "from Course c " +
            "where ?1 member of c.students")
    List<Course> findAllForStudent(StudentEntity student);

    @Query("select case when count(c) > 0 then true else false end " +
            "from Course c " +
            "where c = ?2 and ?1 member of c.students")
    boolean existsCourseRegistration(StudentEntity student,
            Course course);

    List<Course> findAllBySemester(Semester entity);
}
