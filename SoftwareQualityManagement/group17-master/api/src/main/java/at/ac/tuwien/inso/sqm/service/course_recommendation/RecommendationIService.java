package at.ac.tuwien.inso.sqm.service.course_recommendation; //FIXME package
// naming convention?!

import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface RecommendationIService {

    /**
     * Recommends courses for a student by first filtering courses, running
     * all the course scorers,
     * normalizing the results and scaling each scorer function by weight.
     * Finally, the list is
     * sorted by score.
     * <p>
     * The user needs to be authenticated.
     *
     * @param student The student that needs recommendations
     * @return a sorted list of courses by weighted score
     */
    @PreAuthorize("isAuthenticated()")
    List<Course> recommendCourses(StudentEntity student);

    /**
     * Recommends courses, whereby the list contains no more than a number of
     * entries defined as a
     * constant in the implementation.
     * <p>
     * The user needs to be authenticated.
     *
     * @param student
     * @return A sorted list of courses
     */
    @PreAuthorize("isAuthenticated()")
    List<Course> recommendCoursesSublist(StudentEntity student);
}
