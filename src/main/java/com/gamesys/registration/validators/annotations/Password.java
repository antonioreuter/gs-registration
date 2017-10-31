package com.gamesys.registration.validators.annotations;

import com.gamesys.registration.validators.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "The password must have at least one uppercase letter, at least one digit and at least 4 characters.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
