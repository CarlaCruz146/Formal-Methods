package at.ac.tuwien.inso.sqm.controller.admin.forms.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = UniqueIdentificationNumberConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueIdentificationNumber {

    /**
     * Gets the UniqueIdentificationNumber as message.
     * @return the message
     */
    String message() default "{UniqueIdentificationNumber}";

    /**
     * Gets the groups.
     * @return the groups
     */
    Class<?>[] groups() default {};

    /**
     * Gets the payload.
     * @return the payload
     */
    Class<? extends Payload>[] payload() default {};

}