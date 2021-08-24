package at.ac.tuwien.inso.sqm.security;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Ignore
public class UserCreationServiceSecurityTests {
    //
    //    @Autowired
    //    private UserCreationService userCreationService;
    //
    //    private UisUserEntity user = new StudentEntity("student1",
    //    "student", "student@student.com");
    //
    //
    //    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    //    public void createNotAuthenticated() {
    //        userCreationService.create(user);
    //    }
    //
    //    @Test(expected = AccessDeniedException.class)
    //    @WithMockUser(roles = "STUDENT")
    //    public void createAuthenticatedAsStudent() {
    //        userCreationService.create(user);
    //    }
    //
    //    @Test(expected = AccessDeniedException.class)
    //    @WithMockUser(roles = "LECTURER")
    //    public void createAuthenticatedAsLecturer() {
    //        userCreationService.create(user);
    //    }
    //
    //    @Test(expected = NullPointerException.class)
    //    @WithMockUser(roles = "ADMIN")
    //    public void createAuthenticatedAsAdmin() {
    //        userCreationService.create(user);
    //    }

}
