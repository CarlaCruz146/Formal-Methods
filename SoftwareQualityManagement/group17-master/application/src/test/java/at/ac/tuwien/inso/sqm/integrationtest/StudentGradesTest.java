package at.ac.tuwien.inso.sqm.integrationtest;

import at.ac.tuwien.inso.sqm.entity.Role;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.UserAccountEntity;
import at.ac.tuwien.inso.sqm.repository.StduentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
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
public class StudentGradesTest {

    private final UserAccountEntity studentUser1 =
            new UserAccountEntity("student12", "pass", Role.STUDENT);
    private final UserAccountEntity student22User =
            new UserAccountEntity("student22", "pass", Role.STUDENT);
    private final StudentEntity student12 =
            new StudentEntity("s000001", "Student12", "email@1.com",
                    studentUser1);
    private final StudentEntity student22 =
            new StudentEntity("s000002", "Student22", "email@2.com",
                    student22User);
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StduentRepository stduentRepository;

    @Before
    public void setUp() {
        stduentRepository.save(asList(student12, student22));
    }

    @Test
    public void itShowsEmptyGrades() throws Exception {
        mockMvc.perform(get("/student/grades").with(user(studentUser1)))
                .andExpect(model().attributeExists("grades"));
    }

    @Test
    public void itShowsGrades() throws Exception {
        mockMvc.perform(get("/student/grades").with(user(student22User)))
                .andExpect(model().attributeExists("grades"));
    }
}
