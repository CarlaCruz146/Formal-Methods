package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
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

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TagFrequencyScorerTests {

    private final Map<Tag, Double> expectedTagFrequencies =
            new HashMap<Tag, Double>() {
                {
                    put(new Tag("tag1"), 0.5);
                    put(new Tag("tag2"), 4.3);
                    put(new Tag("tag3"), 3.1);
                    put(new Tag("tag4"), 0.5);
                    put(new Tag("tag5"), -0.5);
                }
            };
    private final Map<Course, Double> expectedScoredCourses =
            new HashMap<Course, Double>() {
                {
                    put(courses.get(3),
                            expectedTagFrequencies.get(new Tag("tag3")) +
                                    expectedTagFrequencies
                                            .get(new Tag("tag2")));
                    put(courses.get(1),
                            expectedTagFrequencies.get(new Tag("tag1")) +
                                    expectedTagFrequencies
                                            .get(new Tag("tag4")) +
                                    expectedTagFrequencies
                                            .get(new Tag("tag2")));
                    put(courses.get(0),
                            expectedTagFrequencies.get(new Tag("tag2")));
                    put(courses.get(2),
                            expectedTagFrequencies.get(new Tag("tag3")));
                    put(courses.get(4),
                            expectedTagFrequencies.get(new Tag("tag5")) +
                                    expectedTagFrequencies
                                            .get(new Tag("tag2")));
                }
            };
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
    @Mock
    private LecturerEntity lecturer;
    @Mock
    private TagFrequencyCalculatorImpl tagFrequencyCalculator;
    @InjectMocks
    private TagFrequencyScoorer tagFrequencyScorer;

    @Before
    public void setUp() throws Exception {
        when(courseRepository.findAllForStudent(student)).thenReturn(courses);
        when(tagFrequencyCalculator.calculate(student))
                .thenReturn(expectedTagFrequencies);
    }


    @Test
    public void itScoresCoursesByTagFrequency() throws Exception {
        Map<Course, Double> scoredCourses =
                tagFrequencyScorer.score(courses, student);

        assertEquals(expectedScoredCourses, scoredCourses);
    }
}
