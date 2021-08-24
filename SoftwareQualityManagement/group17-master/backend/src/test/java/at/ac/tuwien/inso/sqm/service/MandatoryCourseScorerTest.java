package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.StudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.StudyPlanRegistration;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.SubjectForStudyPlanEntity;
import at.ac.tuwien.inso.sqm.repository.SubjectForStudyPlanRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MandatoryCourseScorerTest {

    private final StudentEntity student = mock(StudentEntity.class);
    private final List<Subject> subjects =
            asList(mock(Subject.class), mock(Subject.class),
                    mock(Subject.class));
    private final List<Course> courses =
            asList(new Course(subjects.get(0)),
                    new Course(subjects.get(1)),
                    new Course(subjects.get(2)));
    private final List<StudyPlanEntity> studyPlans =
            asList(mock(StudyPlanEntity.class), mock(StudyPlanEntity.class),
                    mock(StudyPlanEntity.class));
    private final List<SubjectForStudyPlanEntity> subjectsForStudyPlan =
            asList(new SubjectForStudyPlanEntity(subjects.get(0),
                            studyPlans.get(0), false),
                    new SubjectForStudyPlanEntity(subjects.get(0),
                            studyPlans.get(1), true),
                    new SubjectForStudyPlanEntity(subjects.get(1),
                            studyPlans.get(1), false),
                    new SubjectForStudyPlanEntity(subjects.get(0),
                            studyPlans.get(2), true),
                    new SubjectForStudyPlanEntity(subjects.get(1),
                            studyPlans.get(2), true));
    private final List<StudyPlanRegistration> studyPlanRegistrations =
            asList(mock(StudyPlanRegistration.class),
                    mock(StudyPlanRegistration.class),
                    mock(StudyPlanRegistration.class));
    @InjectMocks
    private MandatoryCourseScorer mandatoryCourseScorer;
    @Mock
    private SubjectForStudyPlanRepository subjectForStudyPlanRepository;

    @Test
    public void verifyCourseScorerWithNoMandatorySubjectsAndOneStudyPlan()
            throws Exception {
        when(student.getStudyplans())
                .thenReturn(asList(studyPlanRegistrations.get(0)));
        when(studyPlanRegistrations.get(0).getStudyplan())
                .thenReturn(studyPlans.get(0));
        when(subjectForStudyPlanRepository
                .findBySubjectInAndStudyPlan(subjects, studyPlans.get(0)))
                .thenReturn(asList(subjectsForStudyPlan.get(0)));

        Map<Course, Double> scores =
                mandatoryCourseScorer.score(courses, student);
        Map<Course, Double> expectedScores =
                courses.stream().collect(toMap(identity(), it -> 0.0));

        assertEquals(expectedScores, scores);
    }

    @Test
    public void verifyCourseScorerWithMandatorySubjectsAndOneStudyPlan()
            throws Exception {
        when(student.getStudyplans())
                .thenReturn(asList(studyPlanRegistrations.get(1)));
        when(studyPlanRegistrations.get(1).getStudyplan())
                .thenReturn(studyPlans.get(1));
        when(subjectForStudyPlanRepository
                .findBySubjectInAndStudyPlan(subjects, studyPlans.get(1)))
                .thenReturn(asList(subjectsForStudyPlan.get(1),
                        subjectsForStudyPlan.get(2)));

        Map<Course, Double> scores =
                mandatoryCourseScorer.score(courses, student);
        Map<Course, Double> expectedScores =
                new HashMap<Course, Double>() {
                    {
                        put(courses.get(0), 3.0);
                        put(courses.get(1), 0.0);
                        put(courses.get(2), 0.0);
                    }
                };

        assertEquals(expectedScores, scores);
    }

    @Test
    public void verifyCourseScorerWithMandatorySubjectsAndMoreStudyPlans()
            throws Exception {
        when(student.getStudyplans()).thenReturn(
                asList(studyPlanRegistrations.get(1),
                        studyPlanRegistrations.get(2)));
        when(studyPlanRegistrations.get(1).getStudyplan())
                .thenReturn(studyPlans.get(1));
        when(studyPlanRegistrations.get(2).getStudyplan())
                .thenReturn(studyPlans.get(2));
        when(subjectForStudyPlanRepository
                .findBySubjectInAndStudyPlan(subjects, studyPlans.get(1)))
                .thenReturn(asList(subjectsForStudyPlan.get(1),
                        subjectsForStudyPlan.get(2)));
        when(subjectForStudyPlanRepository
                .findBySubjectInAndStudyPlan(subjects, studyPlans.get(2)))
                .thenReturn(asList(subjectsForStudyPlan.get(3),
                        subjectsForStudyPlan.get(4)));

        Map<Course, Double> scores =
                mandatoryCourseScorer.score(courses, student);
        Map<Course, Double> expectedScores =
                new HashMap<Course, Double>() {
                    {
                        put(courses.get(0), 6.0);
                        put(courses.get(1), 3.0);
                        put(courses.get(2), 0.0);
                    }
                };

        assertEquals(expectedScores, scores);
    }
}
