package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.dto.CourseDetailsForStudent;
import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.Semester;
import at.ac.tuwien.inso.sqm.entity.SemesterTypeEnum;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.SubjectForStudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.Tag;
import at.ac.tuwien.inso.sqm.exception.BusinessObjectNotFoundException;
import at.ac.tuwien.inso.sqm.repository.CourseRepository;
import at.ac.tuwien.inso.sqm.repository.SubjectForStudyPlanRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CourseDetailsTest {

    private final Semester olderSemester =
            new Semester(2015, SemesterTypeEnum.WinterSemester);
    private final Semester currentSemester =
            new Semester(2016, SemesterTypeEnum.WinterSemester);

    private final Subject subject = new Subject("subject", BigDecimal.ONE);

    private final StudentEntity student =
            new StudentEntity("1", "student", "student@uis.at");

    private final Long courseId = 1L;
    private final Course course =
            new Course(subject, currentSemester, "some description");
    @InjectMocks
    private final LehrveranstaltungServiceInterface courseService =
            new CourseServiceImpl();
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private SemesterServiceInterface semesterService;
    @Mock
    private SubjectForStudyPlanRepository subjectForStudyPlanRepository;

    @Before
    public void setUp() throws Exception {

        when(semesterService.getCurrentSemester())
                .thenReturn(currentSemester.toDto());
        when(semesterService.getOrCreateCurrentSemester())
                .thenReturn(currentSemester.toDto());

        when(subjectForStudyPlanRepository.findBySubject(subject))
                .thenReturn(emptyList());

        when(courseRepository.findOne(courseId)).thenReturn(course);
    }

    @Test(expected = BusinessObjectNotFoundException.class)
    public void itThrowsOnUnknownCourse() throws Exception {
        courseService.courseDetailsFor(student, 2L);
    }

    @Test
    public void testCanEnrollForCourseInOlderSemester() throws Exception {
        when(courseRepository.findOne(courseId))
                .thenReturn(new Course(subject, olderSemester));

        CourseDetailsForStudent details =
                courseService.courseDetailsFor(student, courseId);

        assertFalse(details.getCanEnroll());
    }

    @Test
    public void testCanEnrollForCourseAlreadyRegisteredFor() throws Exception {
        when(courseRepository.existsCourseRegistration(student, course))
                .thenReturn(true);

        CourseDetailsForStudent details =
                courseService.courseDetailsFor(student, courseId);

        assertFalse(details.getCanEnroll());
    }

    @Test
    public void testCanEnrollForCourseInCurrentSemesterNotRegisteredFor()
            throws Exception {
        when(courseRepository.existsCourseRegistration(student, course))
                .thenReturn(false);

        CourseDetailsForStudent details =
                courseService.courseDetailsFor(student, courseId);

        assertTrue(details.getCanEnroll());
    }

    @Test
    public void testGeneralDetails() throws Exception {
        CourseDetailsForStudent details =
                courseService.courseDetailsFor(student, courseId);

        assertEquals(subject.getName(), details.getName());
        assertEquals(subject.getEcts(), details.getEcts());
        assertEquals(currentSemester.getLabel(), details.getSemester());
        assertEquals(course.getDescription(), details.getDescription());
    }

    @Test
    public void testTags() throws Exception {
        course.addTags(new Tag("tag 1"), new Tag("tag 2"));
        List<String> tags =
                course.getTags().stream().map(Tag::getName).collect(toList());

        CourseDetailsForStudent details =
                courseService.courseDetailsFor(student, courseId);

        assertEquals(tags, details.getTags());
    }

    @Test
    public void testLecturers() throws Exception {
        subject.addLecturers(
                new LecturerEntity("2", "lecturer", "lecturer@uis.at"));

        CourseDetailsForStudent details =
                courseService.courseDetailsFor(student, courseId);

        assertEquals(subject.getLecturers(), details.getLecturers());
    }

    @Test
    public void testStudyPlans() throws Exception {
        List<SubjectForStudyPlanEntity> studyplans =
                singletonList(mock(SubjectForStudyPlanEntity.class));
        when(subjectForStudyPlanRepository.findBySubject(subject))
                .thenReturn(studyplans);

        CourseDetailsForStudent details =
                courseService.courseDetailsFor(student, courseId);

        assertEquals(studyplans, details.getStudyplans());
    }

}
