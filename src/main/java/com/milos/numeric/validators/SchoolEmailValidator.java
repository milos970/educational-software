package com.milos.numeric.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SchoolEmailValidator implements ConstraintValidator<SchoolEmail, String>
{
    private final static String regexStudentEmail = "\\b[A-Za-z0-9._%+-]+@stud\\.uniza\\.sk\\b";
    private final static String regexEmployeeEmail = "\\b[A-Za-z0-9._%+-]+@fri\\.uniza\\.sk\\b";
    private final static Pattern patternStudent = Pattern.compile(regexStudentEmail);
    private final static Pattern patternEmployee = Pattern.compile(regexEmployeeEmail);

    @Override
    public void initialize(SchoolEmail constraintAnnotation)
    {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext)
    {
        Matcher matcherA = patternStudent.matcher(email);
        Matcher matcherB = patternEmployee.matcher(email);
        return matcherA.matches() || matcherB.matches();
    }
}
