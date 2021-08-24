package at.ac.tuwien.inso.sqm.controller;

import at.ac.tuwien.inso.sqm.entity.UserAccountEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(LoginController.class);

    /**
     *
     * @param loggedOut if the user is logged out
     * @param error if there is an error
     * @param invalidSession if there is invalid session
     * @param notLoggedIn if the user is not logged in
     * @param session the session
     * @param request the request
     * @param model the model
     * @param redirectAttributes the redirect attributes
     * @return the redirection page
     */
    @GetMapping("/login")
    public String getLogin(@RequestParam(value = "loggedOut", required = false)
            String loggedOut,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "invalidSession", required = false)
                    String invalidSession,
            @RequestParam(value = "notLoggedIn", required = false)
                    String notLoggedIn, HttpSession session,
            HttpServletRequest request, Model model,
            RedirectAttributes redirectAttributes) {

        // Show a flash messages after a logout
        if (loggedOut != null) {
            redirectAttributes
                    .addFlashAttribute("flashMessage", "login.loggedOut");
            return "redirect:/login";
        }

        // Show a message after an error
        if (error != null) {
            Exception lastException = (Exception) session
                    .getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

            if (lastException != null) {
                redirectAttributes.addFlashAttribute("loginError",
                        lastException.getLocalizedMessage());
            }

            LOGGER.info("Removing ?error parameter from /login");
            return "redirect:/login";
        }

        // Show a message after the session became invalid
        if (invalidSession != null) {
            redirectAttributes
                    .addFlashAttribute("flashMessage", "login.invalidSession");
            LOGGER.info("Removing ?invalidSession parameter from /login");
            return "redirect:/login";
        }

        // Show a message, that the user has to login first
        if (notLoggedIn != null) {
            redirectAttributes
                    .addFlashAttribute("flashMessage", "login.notLoggedIn");
            LOGGER.info("Removing ?notLoggedIn parameter from /login");
            return "redirect:/login";
        }

        // Redirect to "/" if the user is already logged in
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        Object userAccount = auth.getPrincipal();

        if (userAccount != null && userAccount instanceof UserAccountEntity) {
            UserAccountEntity castUserAccount = (UserAccountEntity) userAccount;
            Long userId = castUserAccount.getId();

            if (userId != null && userId > 0) {
                LOGGER.info("User " + userId + " is already logged in");
                redirectAttributes.addFlashAttribute("flashMessage",
                        "login.already-logged-in");
                return "redirect:/";
            }
        }

        return "login";
    }
}
