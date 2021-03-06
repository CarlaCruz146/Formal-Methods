package at.ac.tuwien.inso.sqm.integrationtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Arrays.asList;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class LecturerSubjectsTest extends AbstractSubjectsTest {

    @Test
    public void lecturersShouldSeeTheirOwnSubjects() throws Exception {

        // when subjects are created and assigned to lecturers
        sepm.addLecturers(lecturer1);
        ase.addLecturers(lecturer1, lecturer2);
        subjectRepository.save(calculus);
        subjectRepository.save(sepm);
        subjectRepository.save(ase);

        // lecturer1 should see sepm and ase
        mockMvc.perform(get("/lecturer/subjects")
                .with(user("lecturer1").roles("LECTURER"))).andExpect(
                model().attribute("ownedSubjects", asList(sepm, ase)));

        // lecturer2 should see ase
        mockMvc.perform(get("/lecturer/subjects")
                .with(user("lecturer2").roles("LECTURER")))
                .andExpect(model().attribute("ownedSubjects", asList(ase)));

        // lecturer3 should see nothing
        mockMvc.perform(get("/lecturer/subjects")
                .with(user("lecturer3").roles("LECTURER")))
                .andExpect(model().attribute("ownedSubjects", asList()));

    }
}
