package at.ac.tuwien.inso.sqm.integrationtest;

import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class LecturerAddCourseTest extends AbstractCoursesTest {

    @Test
    public void itCreatesCourseTest() throws Exception {

        // given lecturer having subject "maths"
        Subject maths = subjectRepository
                .save(new Subject("maths", new BigDecimal(6.0)));
        maths.addLecturers(lecturer1);
        Course course = new Course(maths, ss2021);
        course.setStudentLimits(20);

        // when creating a new course
        mockMvc.perform(post("/lecturer/addCourse").with(user(user1))
                .param("course.studentLimits",
                        String.valueOf(course.getStudentLimits()))
                .param("subjectId", maths.getId().toString()).with(csrf()))
                .andExpect(redirectedUrl("/lecturer/courses"))
                .andExpect(flash().attributeExists("flashMessageNotLocalized"));

        Course created =
                courseRepository.findAllBySubject(maths).get(0);
        assertTrue(findCourses(lecturer1).contains(created));
    }

    @Test
    public void itDoesNotCreateCourseWithNegativeStudentLimitsTest()
            throws Exception {

        // given lecturer having subject "maths"
        Subject maths = subjectRepository
                .save(new Subject("maths", new BigDecimal(6.0)));
        maths.addLecturers(lecturer1);
        Course course = new Course(maths, ws2016);
        course.setStudentLimits(-1);

        // when creating a new course with negative student limits
        mockMvc.perform(post("/lecturer/addCourse").with(user(user1))
                .param("course.studentLimits",
                        String.valueOf(course.getStudentLimits()))
                .param("subjectId", maths.getId().toString()).with(csrf()))
                .andExpect(redirectedUrl("/lecturer/courses"))
                .andExpect(flash().attributeExists("flashMessageNotLocalized"));

        assertTrue(courseRepository.findAllBySubject(maths).size() == 0);
    }

    @Test
    public void getAddCoursePageTest() throws Exception {

        // given lecturer having subject "maths"
        Subject maths = subjectRepository
                .save(new Subject("maths", new BigDecimal(6.0)));
        maths.addLecturers(lecturer1);

        // when navigation to the new course page
        mockMvc.perform(get("/lecturer/addCourse").with(user(user1))
                .param("subjectId", maths.getId().toString()))
                .andExpect(model().attribute("subject", maths));
    }

    public List<Course> findCourses(LecturerEntity lecturer) {
        Iterable<Subject> subjectsForLecturer =
                subjectRepository.findByLecturersId(lecturer.getId());
        List<Course> courses = new ArrayList<>();
        subjectsForLecturer.forEach(subject -> courses.addAll(courseRepository
                .findAllBySemesterAndSubject(ss2021, subject)));
        return courses;
    }
}
