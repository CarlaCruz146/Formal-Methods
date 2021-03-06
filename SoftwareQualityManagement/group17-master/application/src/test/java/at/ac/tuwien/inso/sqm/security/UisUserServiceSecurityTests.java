package at.ac.tuwien.inso.sqm.security;


import at.ac.tuwien.inso.sqm.exception.BusinessObjectNotFoundException;
import at.ac.tuwien.inso.sqm.service.UisUserServiceInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UisUserServiceSecurityTests {

    @Autowired
    private UisUserServiceInterface uisUserService;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void findAllMatchingNotAuthenticated() {
        uisUserService.findAllMatching("", null);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "STUDENT")
    public void findAllMatchingAuthenticatedAsStudent() {
        uisUserService.findAllMatching("", null);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "LECTURER")
    public void findAllMatchingAuthenticatedAsLecturer() {
        uisUserService.findAllMatching("", null);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void findAllMatchingAuthenticatedAsAdmin() {
        uisUserService.findAllMatching("", null);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void findOneNotAuthenticated() {
        uisUserService.findOne(Long.valueOf(1));
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "STUDENT")
    public void findOneAuthenticatedAsStudent() {
        uisUserService.findOne(Long.valueOf(1));
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "LECTURER")
    public void findOneAuthenticatedAsLecturer() {
        uisUserService.findOne(Long.valueOf(1));
    }

    @Test(expected = BusinessObjectNotFoundException.class)
    @WithMockUser(roles = "ADMIN")
    public void findOneAuthenticatedAsAdmin() {
        uisUserService.findOne(Long.valueOf(1));
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void existsUserWithIdentificationNumberNotAuthenticated() {
        uisUserService.existsUserWithIdentificationNumber("");
    }

    @Test
    @WithMockUser
    public void existsUserWithIdentificationNumberAuthenticated() {
        uisUserService.existsUserWithIdentificationNumber("");
    }

}
