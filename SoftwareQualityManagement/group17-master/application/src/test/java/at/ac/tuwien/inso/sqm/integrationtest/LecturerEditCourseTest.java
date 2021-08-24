package at.ac.tuwien.inso.sqm.integrationtest;

import at.ac.tuwien.inso.sqm.dto.AddCourseForm;
import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.MarkEntity;
import at.ac.tuwien.inso.sqm.entity.Role;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.UserAccountEntity;
import at.ac.tuwien.inso.sqm.repository.GradeRepository;
import at.ac.tuwien.inso.sqm.repository.LecturerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
public class LecturerEditCourseTest extends AbstractCoursesTest {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Test
    public void testRemoveCourseWithoutStudentsOrGradesByIdSuccessfullyRemovesCourse()
            throws Exception {
        Course c = aseSS2021;
        assertTrue(courseRepository.exists(c.getId()));

        //lecturerRepository.save(new LecturerEntity("testLec3", "lecturer3",
        // "lecturer3@uis.at", new UserAccountEntity("lecturer3", "pass",
        // Rolle.LECTURER)));

        mockMvc.perform(
                post("/lecturer/editCourse/remove?courseId=" + c.getId())
                        .with(user("lecturer3").roles(Role.LECTURER.name()))
                        .with(csrf()))
                .andExpect(redirectedUrl("/lecturer/courses"));
        assertTrue(!courseRepository.exists(c.getId()));
    }

    @Test
    public void testRemoveCourseWithNoIdGetsError() throws Exception {

        mockMvc.perform(get("/lecturer/editCourse/remove?courseId=")
                .with(user("lecturer3").roles(Role.LECTURER.name())))
                .andExpect(redirectedUrl(null));
    }

    @Test
    public void testRemoveCourseWithStudentsDoesNotWork() throws Exception {

        Course c = calculusSS2021;
        assertTrue(courseRepository.exists(c.getId()));
        mockMvc.perform(
                post("/lecturer/editCourse/remove?courseId=" + c.getId())
                        .with(user("lecturer3").roles(Role.LECTURER.name()))
                        .with(csrf()))
                .andExpect(redirectedUrl("/lecturer/courses"));
        assertTrue(courseRepository.exists(c.getId()));
    }

    @Test
    public void testRemoveCourseWithGradesDoesNotWork() throws Exception {
        Course c = aseSS2021;
        Grade grade = new Grade(c, lecturer3, student, MarkEntity.GOOD);
        gradeRepository.save(grade);
        assertTrue(courseRepository.exists(c.getId()));
        assertTrue(gradeRepository.findByCourseId(c.getId()).size() > 0);
        mockMvc.perform(
                post("/lecturer/editCourse/remove?courseId=" + c.getId())
                        .with(user("lecturer3").roles(Role.LECTURER.name()))
                        .with(csrf()))
                .andExpect(redirectedUrl("/lecturer/courses"));
        assertTrue(courseRepository.exists(c.getId()));
    }

    @Test
    public void testRemoveCourseWithGradesAndStudentsDoesNotWork()
            throws Exception {
        Course c = calculusSS2021;
        Grade grade = new Grade(c, lecturer3, student, MarkEntity.GOOD);
        gradeRepository.save(grade);
        assertTrue(courseRepository.exists(c.getId()));
        assertTrue(gradeRepository.findByCourseId(c.getId()).size() != 0);
        mockMvc.perform(
                post("/lecturer/editCourse/remove?courseId=" + c.getId())
                        .with(user("lecturer3").roles(Role.LECTURER.name()))
                        .with(csrf()))
                .andExpect(redirectedUrl("/lecturer/courses"));
        assertTrue(courseRepository.exists(c.getId()));
    }

    @Test
    public void itEditsCourse() throws Exception {
        AddCourseForm form = new AddCourseForm(sepmSS2021);
        form.setInitialTags(tagRepository.findAll());
        form.setInitialActiveTags(sepmSS2021.getTags());
        String testDescription = "TEST DESCRIPTION";
        form.getCourse().setDescription(testDescription);
        form.getCourse().setStudentLimits(20);

        lecturerRepository.save(new LecturerEntity("testLec", "lecturerTest",
                "lecturerTest@uis.at",
                new UserAccountEntity("lecturerTest", "pass", Role.LECTURER)));

        mockMvc.perform(post("/lecturer/editCourse")
                .with(user("lecturerTest").roles(Role.LECTURER.name()))
                .content(form.toString())
                .param("courseId", form.getCourse().getId().toString())
                .with(csrf())).andExpect(redirectedUrl("/lecturer/courses"))
                .andExpect(flash().attributeExists("flashMessageNotLocalized"));

        // check the changes were updated
        Course actualCourse =
                courseRepository.findOne(sepmSS2021.getId());
        assertEquals(sepmSS2021, actualCourse);
    }

    @Test
    public void itEditsCourseFail() throws Exception {
        AddCourseForm form = new AddCourseForm(sepmSS2021);
        form.setInitialTags(tagRepository.findAll());
        form.setInitialActiveTags(sepmSS2021.getTags());
        String testDescription = "TEST DESCRIPTION";
        form.getCourse().setDescription(testDescription);
        form.getCourse().setStudentLimits(-20);
        String expected =
                "The student limit of the course needs to be at least 0";
        mockMvc.perform(post("/lecturer/editCourse")
                .with(user("lecturer").roles(Role.LECTURER.name()))
                .content(form.toString())
                .param("courseId", form.getCourse().getId().toString())
                .with(csrf())).andExpect(redirectedUrl("/lecturer/courses"))
                .andExpect(flash().attributeExists("flashMessageNotLocalized"))
                .andExpect(flash().attribute("flashMessageNotLocalized",
                        expected));
    }

    @Test
    public void getEditCoursePageTest() throws Exception {

        // given lecturer having subject "maths" and a course for
        // integrationtest
        Subject maths = subjectRepository
                .save(new Subject("maths", new BigDecimal(6.0)));
        maths.addLecturers(lecturer1);
        Course course = new Course(maths, ws2016);
        course.setStudentLimits(20);
        Course mathsCourse = courseRepository.save(course);

        // when navigation to the edit course page
        mockMvc.perform(get("/lecturer/editCourse").with(user(user1))
                .param("courseId", mathsCourse.getId().toString()))
                .andExpect(model().attribute("course", mathsCourse));
    }
}
