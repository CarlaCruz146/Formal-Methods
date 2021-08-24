package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.Feedback;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.repository.CourseRepository;
import at.ac.tuwien.inso.sqm.repository.FeedbackRepository;
import at.ac.tuwien.inso.sqm.service.course_recommendation.CourseScorer;
import at.ac.tuwien.inso.sqm.service.course_recommendation.StudentSimilarityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
public class UserBasedCourseScorer implements CourseScorer {

    private static final double LIKE = 2;
    private static final double DISLIKE = -1;
    private static final double REGISTERED = 1;

    @Autowired
    private StudentSimilarityService studentSimilarityService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Value("${uis.course.recommender.user.scorer.weight:1}")
    private Double weight;

    @Override
    public double weight() {
        return weight;
    }

    /**
     * Calculates the score for a student and a list of courses.
     * @param courses The list of courses that need to be ranked
     * @param student The student on who the recommendation is based
     * @return the score for a student and a list of courses.
     */
    @Override
    public Map<Course, Double> score(List<Course> courses,
                                     StudentEntity student) {
        Map<Course, Double> courseScores =
                courses.stream().collect(toMap(identity(), it -> 0.0));

        List<StudentEntity> similarStudents =
                studentSimilarityService.getSimilarStudents(student);

        courseScores.keySet()
                .forEach(course -> similarStudents.forEach(similarStudent -> {
                    List<Feedback> similarStudentFeedback =
                            feedbackRepository.findAllOfStudent(similarStudent);

                    /*
                     * get all courses from similar user for the student
                     * course subject
                     *
                     * there might be more courses in case the similar
                     * student did more courses for the subject
                     */
                    List<Course> matchingCourses =
                            courseRepository.findAllForStudent(similarStudent)
                                    .stream()
                                    .filter(studentCourse -> studentCourse
                                            .getSubject()
                                            .equals(course.getSubject()))
                                    .collect(Collectors.toList());

                    Double score = similarStudentFeedback.stream()
                            .filter(feedback -> matchingCourses
                                    .contains(feedback.getCourse()))
                            .mapToDouble(feedback -> feedback.getType()
                                    .equals(Feedback.Type.LIKE) ? LIKE :
                                    DISLIKE).sum();


                    // the similar user did not give feedback for this course
                    // but he was registered to this course
                    if (score != null && score == 0 &&
                            matchingCourses.size() > 0) {
                        score = REGISTERED;
                    }

                    courseScores.put(course, courseScores.get(course) + score);
                }));

        return courseScores;
    }
}
