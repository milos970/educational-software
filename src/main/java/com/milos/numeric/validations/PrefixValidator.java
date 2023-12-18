package com.milos.numeric.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrefixValidator implements ConstraintValidator<NonLinear, String> {

    private String regex = "y\\s*=\\s*\\-?\\d+\\.?\\d*\\s*\\*\\s*x\\s*\\+\\s*\\-?\\d+\\.?\\d*";



    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        return value.matches(this.regex);
    }
}
