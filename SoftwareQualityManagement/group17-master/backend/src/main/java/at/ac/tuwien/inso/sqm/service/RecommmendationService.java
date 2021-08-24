package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.repository.CourseRepository;
import at.ac.tuwien.inso.sqm.service.course_recommendation.CourseRelevanceFilter;
import at.ac.tuwien.inso.sqm.service.course_recommendation.CourseScorer;
import at.ac.tuwien.inso.sqm.service.course_recommendation.RecommendationIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;

@Service
public class RecommmendationService implements RecommendationIService {

    private static final Long N_MAX_COURSE_RECOMMENDATIONS = 10L;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseNormalizer courseNormalizer;

    private List<CourseRelevanceFilter> courseRelevanceFilters;

    private List<CourseScorer> courseScorers;
    private double courseScorersWeights;

    @Autowired
    public RecommmendationService setCourseRelevanceFilters(
            List<CourseRelevanceFilter> courseRelevanceFilters) {
        this.courseRelevanceFilters = courseRelevanceFilters;
        return this;
    }

    @Autowired
    public RecommmendationService setCourseScorers(
            List<CourseScorer> courseScorers) {
        this.courseScorers = courseScorers;
        courseScorersWeights =
                courseScorers.stream().mapToDouble(CourseScorer::weight).sum();
        return this;
    }

    @Override
    public List<Course> recommendCoursesSublist(
            StudentEntity student) {
        List<Course> recommended = recommendCourses(student);
        return recommended.subList(0,
                max(N_MAX_COURSE_RECOMMENDATIONS.intValue(),
                        recommended.size()));
    }

    //TODO FIXME??! why is this method called max when it returns the min?!????!
    private int max(int a, int b) {
        //return a < b ? a : b;
        return Math.min(a, b);
    }

    /**
     * Recommend courses to a student.
     * @param student The student that needs recommendations
     * @return a list with recommended courses
     */
    @Override
    public List<Course> recommendCourses(StudentEntity student) {
        List<Course> courses = getRecommendableCoursesFor(student);

        // Compute initial scores
        Map<CourseScorer, Map<Course, Double>> scores =
                courseScorers.stream().collect(Collectors
                        .toMap(identity(), it -> it.score(courses, student)));

        // Normalize scores
        scores.values().forEach(it -> courseNormalizer.normalize(it));

        // Aggregate scores, by scorer weights
        Map<Course, Double> recommendedCourseMap = courses.stream()
                .collect(Collectors.toMap(identity(), course -> {
                    double aggregatedScore = scores.keySet().stream()
                            .mapToDouble(
                                    scorer -> scores.get(scorer).get(course) *
                                            scorer.weight()).sum();
                    return aggregatedScore / courseScorersWeights;
                }));

        // Sort courses by score
        return recommendedCourseMap.entrySet().stream()
                .sorted(Map.Entry.<Course, Double>comparingByValue()
                        .reversed()).map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private Map<Course, Double> mergeMaps(
            Map<Course, Double> map1,
            Map<Course, Double> map2) {
        return Stream.concat(map1.entrySet().stream(), map2.entrySet().stream())
                .collect(Collectors
                        .toMap(Map.Entry::getKey, Map.Entry::getValue,
                                Double::sum));
    }

    private List<Course> getRecommendableCoursesFor(
            StudentEntity student) {
        List<Course> courses =
                courseRepository.findAllRecommendableForStudent(student);

        for (CourseRelevanceFilter filter : courseRelevanceFilters) {
            courses = filter.filter(courses, student);
        }

        return courses;
    }
}
