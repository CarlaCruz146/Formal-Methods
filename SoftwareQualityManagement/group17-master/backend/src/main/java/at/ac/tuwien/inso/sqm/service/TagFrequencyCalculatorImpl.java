package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.Feedback;
import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.MarkEntity;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.Tag;
import at.ac.tuwien.inso.sqm.repository.CourseRepository;
import at.ac.tuwien.inso.sqm.repository.FeedbackRepository;
import at.ac.tuwien.inso.sqm.repository.GradeRepository;
import at.ac.tuwien.inso.sqm.repository.TagRepository;
import at.ac.tuwien.inso.sqm.service.course_recommendation.TagFrequencyCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TagFrequencyCalculatorImpl implements TagFrequencyCalculator {

    public static final Map<MarkEntity, Double> GRADE_WEIGHTS =
            new HashMap<MarkEntity, Double>() {
                {
                    put(MarkEntity.EXCELLENT, 0.5);
                    put(MarkEntity.GOOD, 0.3);
                    put(MarkEntity.SATISFACTORY, 0.1);
                    put(MarkEntity.SUFFICIENT, 0.1);
                    put(MarkEntity.FAILED, -0.5);
                }
            };

    public static final Map<Feedback.Type, Double> FEEDBACK_WEIGHTS =
            new HashMap<Feedback.Type, Double>() {
                {
                    put(Feedback.Type.LIKE, 1.0);
                    put(Feedback.Type.DISLIKE, -1.0);
                }
            };

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public Map<Tag, Double> calculate(StudentEntity student) {
        List<Course> courses =
                courseRepository.findAllForStudent(student);
        List<Grade> grades = gradeRepository.findAllOfStudent(student);
        List<Feedback> feedbacks = feedbackRepository.findAllOfStudent(student);

        Map<Tag, Double> tagFrequencies = calculateTagFrequency(courses);
        Map<Tag, Double> tagFrequenciesWithGrades =
                calculateTagFrequencyWithGrades(courses, grades);
        Map<Tag, Double> tagFrequenciesWithFeedback =
                calculateTagFrequencyWithFeedback(courses, feedbacks);

        return mergeTagFrequency(
                mergeTagFrequency(tagFrequencies, tagFrequenciesWithGrades),
                tagFrequenciesWithFeedback);
    }

    private Map<Tag, Double> calculateTagFrequency(
            List<Course> courses) {
        Map<Tag, Double> tags = new HashMap<>();
        courses.forEach(course -> course.getTags()
                .forEach(it -> tags.put(it, tags.getOrDefault(it, 0.0) + 1)));

        return tags;
    }

    private Map<Tag, Double> calculateTagFrequencyWithGrades(
            List<Course> courses, List<Grade> grades) {
        Map<Tag, Double> tagsWithGrades = new HashMap<>();
        courses.forEach(course -> {
            double score = grades.stream()
                    .filter(grade -> grade.getCourse().equals(course))
                    .map(grade -> GRADE_WEIGHTS
                            .getOrDefault(grade.getMark(), 0.0)).findAny()
                    .orElse(0.0);

            course.getTags().forEach(tag -> tagsWithGrades
                    .put(tag, tagsWithGrades.getOrDefault(tag, 0.0) + score));
        });

        return tagsWithGrades;
    }

    private Map<Tag, Double> calculateTagFrequencyWithFeedback(
            List<Course> courses, List<Feedback> feedbacks) {
        Map<Tag, Double> tagsWithFeedback = new HashMap<>();

        courses.forEach(course -> {
            double score = feedbacks.stream()
                    .filter(feedback -> feedback.getCourse().equals(course))
                    .map(feedback -> FEEDBACK_WEIGHTS
                            .getOrDefault(feedback.getType(), 0.0)).findAny()
                    .orElse(0.0);

            course.getTags().forEach(tag -> tagsWithFeedback
                    .put(tag, tagsWithFeedback.getOrDefault(tag, 0.0) + score));
        });

        return tagsWithFeedback;
    }

    private Map<Tag, Double> mergeTagFrequency(Map<Tag, Double> map1,
            Map<Tag, Double> map2) {
        return Stream.concat(map1.entrySet().stream(), map2.entrySet().stream())
                .collect(Collectors
                        .toMap(Map.Entry::getKey, Map.Entry::getValue,
                                Double::sum));
    }
}
