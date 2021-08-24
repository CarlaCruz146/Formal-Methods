package at.ac.tuwien.inso.sqm.controller;

import at.ac.tuwien.inso.sqm.entity.Role;
import at.ac.tuwien.inso.sqm.entity.UserAccountEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/")
public class RootController {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(RootController.class);

    /**
     * Gets the login page.
     * @param model the model
     * @param redirectAttributes the redirection attributes
     * @return the redirection page
     */
    @GetMapping
    public String getLogin(Model model, RedirectAttributes redirectAttributes) {

        // Keep flash attributes in the next view
        Map<String, Object> attributesMap = model.asMap();
        for (String modelKey : attributesMap.keySet()) {
            redirectAttributes
                    .addFlashAttribute(modelKey, attributesMap.get(modelKey));
        }


        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        Object userAccountObject = auth.getPrincipal();

        boolean isInstanceOfUserAccount =
                userAccountObject instanceof UserAccountEntity;
        if (!isInstanceOfUserAccount) {
            LOGGER.info("Redirect from / to /login");
            return "redirect:/login";
        }

        UserAccountEntity userAccount = (UserAccountEntity) userAccountObject;

        if (userAccount.hasRole(Role.ADMIN)) {
            return "redirect:/admin/studyplans";
        } else if (userAccount.hasRole(Role.LECTURER)) {
            return "redirect:/lecturer/courses";
        } else {
            return "redirect:/student/lehrveranstaltungen";
        }
    }
}
