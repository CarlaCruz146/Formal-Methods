package at.ac.tuwien.inso.sqm.controller.admin.forms.validation;

import at.ac.tuwien.inso.sqm.service.UisUserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueIdentificationNumberConstraintValidator
        implements ConstraintValidator<UniqueIdentificationNumber, String> {

    @Autowired
    private UisUserServiceInterface uisUserService;

    public void initialize(UniqueIdentificationNumber constraint) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !uisUserService.existsUserWithIdentificationNumber(value);
    }
}
