package com.milos.numeric.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class EmployeePersonalNumberValidator implements ConstraintValidator<EmployeePersonalNumber, String>
{
    private final static String regex = "\\b[1-9]\\d{5}\\b";
    private final static Pattern pattern = Pattern.compile(regex);

    @Override
    public void initialize(EmployeePersonalNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String pin, ConstraintValidatorContext constraintValidatorContext)
    {
        Matcher matcher = pattern.matcher(pin);
        return matcher.matches();
    }
}
