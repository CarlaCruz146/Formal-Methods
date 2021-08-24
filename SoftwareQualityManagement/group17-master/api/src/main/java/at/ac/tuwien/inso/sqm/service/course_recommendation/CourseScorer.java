package at.ac.tuwien.inso.sqm.service.course_recommendation; //FIXME package
// naming convention?!

import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;

import java.util.List;
import java.util.Map;

public interface CourseScorer {

    /**
     * This method returns the weight the implementation has on the overall
     * recommendation.
     *
     * @return the weight the implementation has on the overall recommendation
     */
    double weight();

    /**
     * This method calculates how important each course is for the specific
     * student. The score is
     * the base for the recommendation, where multiple scorers are combined
     * according to weight
     *
     * @param courses The list of courses that need to be ranked
     * @param student The student on who the recommendation is based
     * @return a map where each course is assigned a score
     */
    Map<Course, Double> score(List<Course> courses,
                              StudentEntity student);
}
