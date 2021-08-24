package at.ac.tuwien.inso.sqm.controller;

import at.ac.tuwien.inso.sqm.controller.forms.AccountActivationForm;
import at.ac.tuwien.inso.sqm.entity.UisUserEntity;
import at.ac.tuwien.inso.sqm.exception.BusinessObjectNotFoundException;
import at.ac.tuwien.inso.sqm.exception.UserFacingException;
import at.ac.tuwien.inso.sqm.service.AccountActivationServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/account_activation/{activationCode}")
public class AccountActivationController {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private AccountActivationServiceInterface accountActivationService;

    @GetMapping
    public String accountActivationView(@PathVariable String activationCode,
            Model model, AccountActivationForm accountActivationForm) {

        try {
            UisUserEntity user =
                    accountActivationService.findOne(activationCode)
                            .getForUser();
            model.addAttribute("user", user);

        } catch (BusinessObjectNotFoundException ex) {
            LOGGER.warn("Account activation failed", ex);
            throw new UserFacingException("error.activation_code.notfound");
        }

        return "account-activation";
    }

    /**
     * Activates the account.
     * @param activationCode the activation code
     * @param accountActivationForm the account activation form
     * @param bindingResult the binding result
     * @param model the model
     * @param attributes the attributes
     * @param response the response
     * @return the page to redirect to
     */
    @PostMapping
    public String activateAccount(@PathVariable String activationCode,
            @Valid AccountActivationForm accountActivationForm,
            BindingResult bindingResult, Model model,
            RedirectAttributes attributes, HttpServletResponse response) {

        UisUserEntity user;
        try {
            user = accountActivationService.findOne(activationCode)
                    .getForUser();
        } catch (BusinessObjectNotFoundException ex) {
            LOGGER.warn("Account activation failed", ex);
            throw new UserFacingException("error.activation_code.notfound");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "account-activation";
        }

        accountActivationService.activateAccount(activationCode,
                accountActivationForm.toUserAccount(user));

        attributes.addFlashAttribute("flashMessage", "account.activated");

        return "redirect:/login";
    }
}
