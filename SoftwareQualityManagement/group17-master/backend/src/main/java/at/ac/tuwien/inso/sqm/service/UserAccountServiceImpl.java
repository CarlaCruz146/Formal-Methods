package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.UserAccountEntity;
import at.ac.tuwien.inso.sqm.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    // UserAccountService extends org.springframework.security.core
    // .userdetails.UserDetailsService


    private static final Logger LOGGER =
            LoggerFactory.getLogger(UserAccountServiceImpl.class);

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    @Transactional(readOnly = true)
    public UserAccountEntity loadUserByUsername(String username)
            throws UsernameNotFoundException {
        LOGGER.info("loading user by username with username " + username);
        UserAccountEntity user = userAccountRepository.findByUsername(username);

        if (user == null) {
            LOGGER.info("cannot find user with username " + username);
            throw new UsernameNotFoundException(username);
        }

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccountEntity getCurrentLoggedInUser() {
        LOGGER.info("getting currently logged in user");
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("returning logged in user with name " + auth.getName());
        return loadUserByUsername(auth.getName());
    }

    @Override
    public boolean existsUsername(String username) {
        LOGGER.info("checking if username " + username + " exists");
        return userAccountRepository.existsByUsername(username);
    }
}
