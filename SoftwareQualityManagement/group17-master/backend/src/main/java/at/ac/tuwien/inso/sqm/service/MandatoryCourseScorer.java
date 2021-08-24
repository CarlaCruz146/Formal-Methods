package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.repository.SubjectForStudyPlanRepository;
import at.ac.tuwien.inso.sqm.service.course_recommendation.CourseScorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class MandatoryCourseScorer implements CourseScorer {

    private static final double MANDATORY = 3;

    @Autowired
    private SubjectForStudyPlanRepository subjectForStudyPlanRepository;

    @Value("${uis.course.recommender.mandatory.scorer.weight:1}")
    private Double weight;

    @Override
    public double weight() {
        return weight;
    }

    /**
     * Calculates the score for the courses and a student.
     * @param courses The list of courses that need to be ranked
     * @param student The student on who the recommendation is based
     * @return a map with the course and respectively its score
     */
    @Override
    public Map<Course, Double> score(List<Course> courses,
                                     StudentEntity student) {
        Map<Course, Double> scoredCourses =
                courses.stream().collect(toMap(identity(), it -> 0.0));
        List<Subject> subjectsForCourses =
                courses.stream().map(course -> course.getSubject())
                        .collect(toList());

        student.getStudyplans().forEach(
                studyPlanRegistration -> subjectForStudyPlanRepository
                        .findBySubjectInAndStudyPlan(subjectsForCourses,
                                studyPlanRegistration.getStudyplan()).stream()
                        .filter(subjectForStudyPlan -> subjectForStudyPlan
                                .getMandatory())
                        .forEach(subjectForStudyPlan -> {
                            Course course = courses.stream()
                                    .filter(it -> it.getSubject()
                                            .equals(subjectForStudyPlan
                                                    .getSubject())).findFirst()
                                    .get();
                            scoredCourses.put(course,
                                    scoredCourses.get(course) + MANDATORY);
                        })

        );

        return scoredCourses;
    }
}
