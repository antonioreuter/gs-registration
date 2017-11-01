package com.gamesys.registration.validators;

import com.gamesys.registration.validators.annotations.Password;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;


public class PasswordValidator implements ConstraintValidator<Password, String> {
    private static final Pattern upperCaseLetter = Pattern.compile("[A-Z]");
    private static final Pattern digit = Pattern.compile("[0-9]");

    @Override
    public void initialize(Password constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(password)) {
            return false;
        }

        boolean hasLetter = upperCaseLetter.matcher(password).find();
        boolean hasDigit = digit.matcher(password).find();

        return (password.length() >= 4) && hasLetter && hasDigit;
    }
}
