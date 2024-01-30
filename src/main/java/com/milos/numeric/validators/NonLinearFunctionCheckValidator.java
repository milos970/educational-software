package com.milos.numeric.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NonLinearFunctionCheckValidator implements ConstraintValidator<NonLinearCheck, String> {

    private String regex = "y\\s*=\\s*\\-?\\d+\\.?\\d*\\s*\\*\\s*x\\s*\\+\\s*\\-?\\d+\\.?\\d*";



    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        return value.matches(this.regex);
    }
}
