package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.EctsDistributionEntity;
import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.MarkEntity;
import at.ac.tuwien.inso.sqm.entity.StudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.SubjectForStudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.SubjectType;
import at.ac.tuwien.inso.sqm.entity.SubjectWithGrade;
import at.ac.tuwien.inso.sqm.exception.BusinessObjectNotFoundException;
import at.ac.tuwien.inso.sqm.exception.ValidationException;
import at.ac.tuwien.inso.sqm.repository.StudyPlanRepository;
import at.ac.tuwien.inso.sqm.repository.SubjectForStudyPlanRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class StudyPlanServiceTest {

    private static final Long VALID_STUDY_PLAN_ID = 1L;
    private static final Long INVALID_STUDY_PLAN_ID = 1337L;
    private final StudentEntity student =
            new StudentEntity("s1234", "student", "s1234@uis.at");
    private final LecturerEntity lecturer =
            new LecturerEntity("l1234", "student", "l1234@uis.at");
    private final StudyPlanEntity studyPlan = new StudyPlanEntity("sp",
            new EctsDistributionEntity(new BigDecimal(60), new BigDecimal(60),
                    new BigDecimal(60)));
    private final SubjectForStudyPlanEntity subjectForStudyPlan =
            new SubjectForStudyPlanEntity(new Subject("subject", null),
                    studyPlan, false);
    private final List<Subject> subjects = new ArrayList<>();
    private final List<SubjectForStudyPlanEntity> subjectsForStudyPlan =
            new ArrayList<>();
    private final List<Grade> grades = new ArrayList<>();
    @Mock
    private StudyPlanRepository studyPlanRepository;
    @Mock
    private SubjectForStudyPlanRepository subjectForStudyPlanRepository;
    @Mock
    private SubjectIService subjectService;
    @Mock
    private GradeIService gradeService;
    @Mock
    private MessageSource messageSource;
    private StudyPlanServiceImpl studyPlanService;

    @Before
    public void setUp() {
        subjects.addAll(asList(new Subject("VU Programmkonstruktion",
                        new BigDecimal(8.8)),
                new Subject("UE Studieneingangsgespräch", new BigDecimal(0.2)),
                new Subject("VU Technische Grundlagen der Informatik",
                        new BigDecimal(6.0)), new Subject(
                        "VO Algebra und Diskrete Mathematik für Informatik " +
                                "und Wirtschaftsinformatik",
                        new BigDecimal(4.0)), new Subject(
                        "UE Algebra und Diskrete Mathematik für Informatik " +
                                "und Wirtschaftsinformatik",
                        new BigDecimal(5.0)),
                new Subject("VU Formale Modellierung", new BigDecimal(3.0)),
                new Subject("VU Datenmodellierung", new BigDecimal(3.0))));
        subjectsForStudyPlan.addAll(asList(
                new SubjectForStudyPlanEntity(subjects.get(0), studyPlan, true),
                new SubjectForStudyPlanEntity(subjects.get(2), studyPlan, true),
                new SubjectForStudyPlanEntity(subjects.get(4), studyPlan, true),
                new SubjectForStudyPlanEntity(subjects.get(5), studyPlan, true),
                new SubjectForStudyPlanEntity(subjects.get(6), studyPlan,
                        true)));
        grades.addAll(asList(new Grade(new Course(subjects.get(0)),
                        lecturer, student, MarkEntity.EXCELLENT),
                new Grade(new Course(subjects.get(2)), lecturer,
                        student, MarkEntity.GOOD),
                new Grade(new Course(subjects.get(4)), lecturer,
                        student, MarkEntity.FAILED)));

        MockitoAnnotations.initMocks(this);
        when(studyPlanRepository.findOne(VALID_STUDY_PLAN_ID))
                .thenReturn(studyPlan);
        when(studyPlanRepository.findOne(INVALID_STUDY_PLAN_ID))
                .thenReturn(null);
        when(subjectForStudyPlanRepository.save(subjectForStudyPlan))
                .thenReturn(subjectForStudyPlan);
        when(subjectService.searchForSubjects(any())).thenReturn(subjects);
        when(subjectForStudyPlanRepository
                .findByStudyPlanIdOrderBySemesterRecommendation(
                        VALID_STUDY_PLAN_ID)).thenReturn(subjectsForStudyPlan);
        when(gradeService.getGradesForLoggedInStudent())
                .thenReturn(new ArrayList(grades));
        studyPlanService = new StudyPlanServiceImpl(studyPlanRepository,
                subjectForStudyPlanRepository, subjectService, gradeService,
                messageSource);
    }

    @After
    public void tearDown() {
        subjects.clear();
        subjectsForStudyPlan.clear();
        grades.clear();
    }

    @Test
    public void getAvailableSubjectsForStudyPlanShouldReturnAvailableSubjectsTest() {
        List<Subject> availableSubjects = studyPlanService
                .getAvailableSubjectsForStudyPlan(VALID_STUDY_PLAN_ID,
                        "some query");
        assertEquals(asList(subjects.get(1), subjects.get(3)),
                availableSubjects);
    }

    @Test
    public void findOneWithValidIdShouldReturnStudyPlanTest() {
        StudyPlanEntity actualStudyPlan =
                studyPlanService.findOne(VALID_STUDY_PLAN_ID);
        assertEquals(studyPlan, actualStudyPlan);
    }

    @Test(expected = BusinessObjectNotFoundException.class)
    public void findOneWithInvalidIdShouldThrowExceptionTest() {
        studyPlanService.findOne(INVALID_STUDY_PLAN_ID);
    }

    @Test(expected = ValidationException.class)
    public void addSubjectToStudyPlanSubjectNullTShouldThrowExceptionTest() {
        subjectForStudyPlan.setSubject(null);
        studyPlanService.addSubjectToStudyPlan(subjectForStudyPlan);
    }

    @Test(expected = ValidationException.class)
    public void addSubjectToStudyPlanSubjectIdNullTShouldThrowExceptionTest() {
        subjectForStudyPlan.getSubject().setId(null);
        studyPlanService.addSubjectToStudyPlan(subjectForStudyPlan);
    }

    @Test(expected = ValidationException.class)
    public void addSubjectToStudyPlanStudyPlanNullTShouldThrowExceptionTest() {
        subjectForStudyPlan.setStudyPlan(null);
        studyPlanService.addSubjectToStudyPlan(subjectForStudyPlan);
    }

    @Test(expected = ValidationException.class)
    public void addSubjectToStudyPlanStudyPlanIdNullTShouldThrowExceptionTest() {
        subjectForStudyPlan.getStudyPlan().setId(null);
        studyPlanService.addSubjectToStudyPlan(subjectForStudyPlan);
    }

    @Test(expected = ValidationException.class)
    public void removeSubjectFromStudyPlanSubjectIdNotNullTest() {

        subjectForStudyPlan.getStudyPlan().setId(null);
        Subject subject = subjects.get(0);

        studyPlanService
                .removeSubjectFromStudyPlan(subjectForStudyPlan.getStudyPlan(),
                        subject);
    }

    @Test(expected = ValidationException.class)
    public void removeSubjectsFromStudyPlanStudyPlanIdNotNullTest() {

        Subject subject = subjects.get(0);
        subject.setId(null);

        studyPlanService
                .removeSubjectFromStudyPlan(subjectForStudyPlan.getStudyPlan(),
                        subject);
    }

    @Test
    public void getSubjectsWithGradesForStudyPlanTest() {
        List<SubjectWithGrade> expectedSubjectsWithTheirGrades =
                studyPlanService
                        .getSubjectsWithGradesForStudyPlan(VALID_STUDY_PLAN_ID);

        assertEquals(expectedSubjectsWithTheirGrades,
                asList(new SubjectWithGrade(subjectsForStudyPlan.get(0),
                                grades.get(0), SubjectType.mandatory),
                        new SubjectWithGrade(subjectsForStudyPlan.get(1),
                                grades.get(1), SubjectType.mandatory),
                        new SubjectWithGrade(subjectsForStudyPlan.get(2),
                                grades.get(2), SubjectType.mandatory),
                        new SubjectWithGrade(subjectsForStudyPlan.get(3),
                                SubjectType.mandatory),
                        new SubjectWithGrade(subjectsForStudyPlan.get(4),
                                SubjectType.mandatory)));
    }
}
