package at.ac.tuwien.inso.sqm.integrationtest;

import at.ac.tuwien.inso.sqm.entity.EctsDistributionEntity;
import at.ac.tuwien.inso.sqm.entity.Feedback;
import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.MarkEntity;
import at.ac.tuwien.inso.sqm.entity.Role;
import at.ac.tuwien.inso.sqm.entity.Semester;
import at.ac.tuwien.inso.sqm.entity.SemesterTypeEnum;
import at.ac.tuwien.inso.sqm.entity.StudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.StudyPlanRegistration;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.UserAccountEntity;
import at.ac.tuwien.inso.sqm.integrationtest.clock.FixedClock;
import at.ac.tuwien.inso.sqm.integrationtest.clock.FixedClockListener;
import at.ac.tuwien.inso.sqm.repository.CourseRepository;
import at.ac.tuwien.inso.sqm.repository.FeedbackRepository;
import at.ac.tuwien.inso.sqm.repository.GradeRepository;
import at.ac.tuwien.inso.sqm.repository.LecturerRepository;
import at.ac.tuwien.inso.sqm.repository.SemestreRepository;
import at.ac.tuwien.inso.sqm.repository.StduentRepository;
import at.ac.tuwien.inso.sqm.repository.StudyPlanRepository;
import at.ac.tuwien.inso.sqm.repository.SubjectRepository;
import at.ac.tuwien.inso.sqm.service.study_progress.CourseRegistrationState;
import at.ac.tuwien.inso.sqm.service.study_progress.CuorseRegistration;
import at.ac.tuwien.inso.sqm.service.study_progress.SemesterProgress;
import at.ac.tuwien.inso.sqm.service.study_progress.StudyProgress;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.math.BigDecimal.ONE;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@FixedClock("2016-05-05T11:00:00Z")
@TestExecutionListeners(
        {TransactionalTestExecutionListener.class, FixedClockListener.class,
         DependencyInjectionTestExecutionListener.class})
public class StudentMyCoursesTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SemestreRepository semesterRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private LecturerRepository lecturerRepository;
    @Autowired
    private StudyPlanRepository studyPlanRepository;
    @Autowired
    private StduentRepository stduentRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;

    private StudentEntity student;

    private List<Semester> semesters;

    private List<Course> courses;

    @Before
    public void setUp() throws Exception {
        student = stduentRepository
                .save(new StudentEntity("123", "student", "mail@uis.at",
                        new UserAccountEntity("student", "pass",
                                Role.STUDENT)));

        prepareSemesters();
        prepareStudyPlans();
        prepareCourses();
        prepareGrades();
        prepareFeedback();
    }

    private void prepareSemesters() {
        semesters = StreamSupport.stream((semesterRepository.save(asList(
                new Semester(2014, SemesterTypeEnum.WinterSemester),
                new Semester(2015, SemesterTypeEnum.SummerSemester),
                new Semester(2015, SemesterTypeEnum.WinterSemester),
                new Semester(2016, SemesterTypeEnum.SummerSemester))))
                .spliterator(), false).collect(Collectors.toList());
    }

    private void prepareStudyPlans() {
        List<StudyPlanEntity> studyPlans = StreamSupport
                .stream((studyPlanRepository.save(asList(
                        new StudyPlanEntity("study 1",
                                new EctsDistributionEntity(ONE, ONE, ONE)),
                        new StudyPlanEntity("study 2",
                                new EctsDistributionEntity(ONE, ONE, ONE)))))
                        .spliterator(), false).collect(Collectors.toList());

        student.addStudyplans(
                new StudyPlanRegistration(studyPlans.get(0), semesters.get(1)),
                new StudyPlanRegistration(studyPlans.get(1), semesters.get(2)));
    }

    private void prepareCourses() {
        Subject subject = subjectRepository.save(new Subject("subject 1", ONE));

        courses = StreamSupport.stream((courseRepository
                .save(asList(new Course(subject, semesters.get(0)),
                        new Course(subject, semesters.get(1)),
                        new Course(subject, semesters.get(1)),
                        new Course(subject, semesters.get(1)),
                        new Course(subject, semesters.get(3)),
                        new Course(subject, semesters.get(3)),
                        new Course(subject, semesters.get(3)))))
                .spliterator(), false).collect(Collectors.toList());

        courses.forEach(it -> it.addStudents(student));
    }

    private void prepareGrades() {
        LecturerEntity lecturer = lecturerRepository
                .save(new LecturerEntity("456", "lecturer", "lecturer@uis.at"));

        gradeRepository.save(asList(new Grade(courses.get(1), lecturer, student,
                        MarkEntity.EXCELLENT),
                new Grade(courses.get(2), lecturer, student, MarkEntity.FAILED),

                new Grade(courses.get(4), lecturer, student,
                        MarkEntity.SUFFICIENT)));
    }

    private void prepareFeedback() {
        feedbackRepository.save(asList(new Feedback(student, courses.get(1)),
                new Feedback(student, courses.get(2)),

                new Feedback(student, courses.get(4)),
                new Feedback(student, courses.get(5))));
    }

    @Test
    public void itShowsStudyProgressForStudent() throws Exception {
        mockMvc.perform(get("/student/meineLehrveranstaltungen")
                .with(user("student").roles(Role.STUDENT.name()))).andExpect(
                model().attribute("studyProgress",
                        new StudyProgress(semesters.get(3).toDto(),
                                asList(new SemesterProgress(
                                                semesters.get(3).toDto(),
                                                asList(new CuorseRegistration(
                                                                courses.get(4),
                                                                CourseRegistrationState.complete_ok),
                                                        new CuorseRegistration(
                                                                courses.get(5),
                                                                CourseRegistrationState.needs_grade),
                                                        new CuorseRegistration(
                                                                courses.get(6),
                                                                CourseRegistrationState.in_progress))),
                                        new SemesterProgress(
                                                semesters.get(2).toDto(),
                                                emptyList()),
                                        new SemesterProgress(
                                                semesters.get(1).toDto(),
                                                asList(new CuorseRegistration(
                                                                courses.get(1),
                                                                CourseRegistrationState.complete_ok),
                                                        new CuorseRegistration(
                                                                courses.get(2),
                                                                CourseRegistrationState.complete_not_ok),
                                                        new CuorseRegistration(
                                                                courses.get(3),
                                                                CourseRegistrationState.needs_feedback)))))));
    }

}
