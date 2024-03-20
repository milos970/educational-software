package com.milos.numeric.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeEmailValidator implements ConstraintValidator<EmployeeEmail, String>
{
    private final static String regex = "\\b[A-Za-z0-9._%+-]+@fri\\.uniza\\.sk\\b";
    private final static Pattern pattern = Pattern.compile(regex);

    @Override
    public void initialize(EmployeeEmail constraintAnnotation)
    {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext)
    {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
