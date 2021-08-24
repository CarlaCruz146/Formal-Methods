package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.Feedback;
import at.ac.tuwien.inso.sqm.entity.Feedback.Type;
import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.MarkEntity;
import at.ac.tuwien.inso.sqm.entity.Semester;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.Tag;
import at.ac.tuwien.inso.sqm.repository.CourseRepository;
import at.ac.tuwien.inso.sqm.repository.FeedbackRepository;
import at.ac.tuwien.inso.sqm.repository.GradeRepository;
import at.ac.tuwien.inso.sqm.repository.StduentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static at.ac.tuwien.inso.sqm.service.TagFrequencyCalculatorImpl.FEEDBACK_WEIGHTS;
import static at.ac.tuwien.inso.sqm.service.TagFrequencyCalculatorImpl.GRADE_WEIGHTS;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TagFrequencyCalculatorTests {

    @Mock
    private StduentRepository stduentRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private GradeRepository gradeRepository;
    @Mock
    private FeedbackRepository feedbackRepository;
    @Mock
    private StudentEntity student;
    @Mock
    private Subject subject;
    @Mock
    private Semester semester;
    @Mock
    private LecturerEntity lecturer;
    @InjectMocks
    private TagFrequencyCalculatorImpl tagFrequencyCalculator;
    private final List<Course> courses =
            asList(new Course(subject, semester)
                            .addTags(new Tag("tag2")),
                    new Course(subject, semester)
                            .addTags(new Tag("tag1"), new Tag("tag4"),
                                    new Tag("tag2")),
                    new Course(subject, semester)
                            .addTags(new Tag("tag3")),
                    new Course(subject, semester)
                            .addTags(new Tag("tag2"), new Tag("tag3")),
                    new Course(subject, semester)
                            .addTags(new Tag("tag5"), new Tag("tag2")));

    private final List<Grade> grades =
            asList(new Grade(courses.get(0), lecturer, student,
                            MarkEntity.GOOD),
                    new Grade(courses.get(1), lecturer, student,
                            MarkEntity.EXCELLENT),
                    new Grade(courses.get(2), lecturer, student,
                            MarkEntity.SUFFICIENT),
                    new Grade(courses.get(4), lecturer, student,
                            MarkEntity.FAILED));

    private final List<Feedback> feedbacks =
            asList(new Feedback(student, courses.get(0), Type.LIKE),
                    new Feedback(student, courses.get(1), Type.DISLIKE),
                    new Feedback(student, courses.get(3), Type.LIKE),
                    new Feedback(student, courses.get(4), Type.DISLIKE));

    private final Map<Tag, Double> tagFrequencies = new HashMap<Tag, Double>() {
        {
            put(new Tag("tag1"), 1.0);
            put(new Tag("tag2"), 4.0);
            put(new Tag("tag3"), 2.0);
            put(new Tag("tag4"), 1.0);
            put(new Tag("tag5"), 1.0);
        }
    };

    private final Map<Tag, Double> tagFrequenciesWithGrades =
            new HashMap<Tag, Double>() {
                {
                    put(new Tag("tag1"),
                            GRADE_WEIGHTS.get(MarkEntity.EXCELLENT));
                    put(new Tag("tag2"), GRADE_WEIGHTS.get(MarkEntity.GOOD) +
                            GRADE_WEIGHTS.get(MarkEntity.EXCELLENT) +
                            GRADE_WEIGHTS.get(MarkEntity.FAILED));
                    put(new Tag("tag3"),
                            GRADE_WEIGHTS.get(MarkEntity.SUFFICIENT));
                    put(new Tag("tag4"),
                            GRADE_WEIGHTS.get(MarkEntity.EXCELLENT));
                    put(new Tag("tag5"), GRADE_WEIGHTS.get(MarkEntity.FAILED));
                }
            };

    private final Map<Tag, Double> getTagFrequenciesWithFeedback =
            new HashMap<Tag, Double>() {
                {
                    put(new Tag("tag1"), FEEDBACK_WEIGHTS.get(Type.DISLIKE));
                    put(new Tag("tag2"), FEEDBACK_WEIGHTS.get(Type.LIKE) +
                            FEEDBACK_WEIGHTS.get(Type.DISLIKE) +
                            FEEDBACK_WEIGHTS.get(Type.LIKE) +
                            FEEDBACK_WEIGHTS.get(Type.DISLIKE));
                    put(new Tag("tag3"), FEEDBACK_WEIGHTS.get(Type.LIKE));
                    put(new Tag("tag4"), FEEDBACK_WEIGHTS.get(Type.DISLIKE));
                    put(new Tag("tag5"), FEEDBACK_WEIGHTS.get(Type.DISLIKE));
                }
            };

    private final Map<Tag, Double> expectedTagFrequencies =
            new HashMap<Tag, Double>() {
                {
                    put(new Tag("tag1"), tagFrequencies.get(new Tag("tag1")) +
                            tagFrequenciesWithGrades.get(new Tag("tag1")) +
                            getTagFrequenciesWithFeedback.get(new Tag("tag1")));
                    put(new Tag("tag2"), tagFrequencies.get(new Tag("tag2")) +
                            tagFrequenciesWithGrades.get(new Tag("tag2")) +
                            getTagFrequenciesWithFeedback.get(new Tag("tag2")));
                    put(new Tag("tag3"), tagFrequencies.get(new Tag("tag3")) +
                            tagFrequenciesWithGrades.get(new Tag("tag3")) +
                            getTagFrequenciesWithFeedback.get(new Tag("tag3")));
                    put(new Tag("tag4"), tagFrequencies.get(new Tag("tag4")) +
                            tagFrequenciesWithGrades.get(new Tag("tag4")) +
                            getTagFrequenciesWithFeedback.get(new Tag("tag4")));
                    put(new Tag("tag5"), tagFrequencies.get(new Tag("tag5")) +
                            tagFrequenciesWithGrades.get(new Tag("tag5")) +
                            getTagFrequenciesWithFeedback.get(new Tag("tag5")));
                }
            };

    @Before
    public void setUp() throws Exception {
        when(courseRepository.findAllForStudent(student)).thenReturn(courses);
        when(gradeRepository.findAllOfStudent(student)).thenReturn(grades);
        when(feedbackRepository.findAllOfStudent(student))
                .thenReturn(feedbacks);
    }

    @Test
    public void itCalculatesTagFrequencies() throws Exception {
        Map<Tag, Double> calculatedTagFrequencies =
                tagFrequencyCalculator.calculate(student);

        assertEquals(expectedTagFrequencies, calculatedTagFrequencies);
    }
}
