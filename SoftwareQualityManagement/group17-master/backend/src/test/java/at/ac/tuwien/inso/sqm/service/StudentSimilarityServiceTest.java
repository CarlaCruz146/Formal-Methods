package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.repository.StduentRepository;
import at.ac.tuwien.inso.sqm.service.course_recommendation.StudentNeighborhoodStore;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StudentSimilarityServiceTest {

    private final StudentEntity student = mock(StudentEntity.class);
    private final UserNeighborhood userNeighborhood =
            mock(UserNeighborhood.class);
    private final List<StudentEntity> students =
            asList(mock(StudentEntity.class), mock(StudentEntity.class),
                    mock(StudentEntity.class));
    @Mock
    private StudentNeighborhoodStore studentNeighborhoodStore;
    @Mock
    private StduentRepository stduentRepository;
    @InjectMocks
    private StduentSimilarityServiceImpl studentSimilarityService;

    @Test
    public void verifySimilarStudentsForAStudentThatHasNoSimilarUsers()
            throws Exception {
        long studentId = 0;
        long[] studentIds = {};

        when(studentNeighborhoodStore.getStudentNeighborhood())
                .thenReturn(userNeighborhood);
        when(userNeighborhood.getUserNeighborhood(studentId))
                .thenReturn(studentIds);

        List<StudentEntity> similarStudents =
                studentSimilarityService.getSimilarStudents(student);

        assertEquals(Collections.emptyList(), similarStudents);
    }

    @Test
    public void verifySimilarStudentsForAStudentThatHasSimilarUsers()
            throws Exception {
        long studentId = 0;
        long[] studentIds = {1, 2, 3};

        when(studentNeighborhoodStore.getStudentNeighborhood())
                .thenReturn(userNeighborhood);
        when(userNeighborhood.getUserNeighborhood(studentId))
                .thenReturn(studentIds);
        when(stduentRepository.findOne(studentIds[0]))
                .thenReturn(students.get(0));
        when(stduentRepository.findOne(studentIds[1]))
                .thenReturn(students.get(1));
        when(stduentRepository.findOne(studentIds[2]))
                .thenReturn(students.get(2));

        List<StudentEntity> similarStudents =
                studentSimilarityService.getSimilarStudents(student);

        assertEquals(students, similarStudents);
    }
}