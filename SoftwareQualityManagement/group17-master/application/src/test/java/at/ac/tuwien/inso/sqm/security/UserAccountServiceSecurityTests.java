package at.ac.tuwien.inso.sqm.security;

import at.ac.tuwien.inso.sqm.service.UserAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class UserAccountServiceSecurityTests {


    @Autowired
    private UserAccountService userAccountService;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getCurrentLoggedInUserNotAuthenticated() {
        userAccountService.getCurrentLoggedInUser();
    }

    @Test(expected = UsernameNotFoundException.class)
    @WithMockUser
    public void getCurrentLoggedInUserAuthenticated() {
        userAccountService.getCurrentLoggedInUser();
    }
}
