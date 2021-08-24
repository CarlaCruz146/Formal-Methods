package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.Semester;
import at.ac.tuwien.inso.sqm.entity.SemesterTypeEnum;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.exception.ValidationException;
import at.ac.tuwien.inso.sqm.repository.SubjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class SubjectServiceTest {

    @InjectMocks
    private final SubjectIService subjectService = new SubjectService();
    @Mock
    private LehrveranstaltungServiceInterface courseService;
    @Mock
    private SubjectRepository subjectRepository;

    @Test
    public void testRemoveWithValidInput() {
        Subject s = new Subject("testSubject", new BigDecimal(11));
        ArrayList<Course> toReturn = new ArrayList<>();
        when(courseService.findCoursesForSubject(s)).thenReturn(toReturn);

        assertTrue(subjectService.remove(s));


    }

    @Test(expected = ValidationException.class)
    public void testRemoveWithNullInputThrowsException() {
        subjectService.remove(null);

    }

    @Test(expected = ValidationException.class)
    public void testRemoveWithContainingCoursesInput() {

        Subject subject = new Subject("testSubject", new BigDecimal(11));

        Semester oldSemester =
                new Semester(2000, SemesterTypeEnum.WinterSemester);

        ArrayList<Course> toReturn = new ArrayList<>();
        toReturn.add(new Course(subject, oldSemester));

        when(courseService.findCoursesForSubject(subject)).thenReturn(toReturn);

        subjectService.remove(subject);
    }
}
