package com.milos.numeric.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TwoDArrayCheckValidator implements ConstraintValidator<NonLinearCheck, String> {

    private String regex = "\\[\\((?!([0-9]+),.*\\1).*?,(?!\\1)([0-9]+)\\)(,(?!\\1)[0-9]+,[0-9]+)+\\]";



    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        return value.matches(this.regex);
    }

}
