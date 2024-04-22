package com.milos.numeric.validators;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class StudentEmailValidator
{
    private final static String regex = "\\b[A-Za-z0-9._%+-]+@stud\\.uniza\\.sk\\b";
    private final static Pattern pattern = Pattern.compile(regex);


    public boolean isValid(String email)
    {
        return pattern.matcher(email).matches();
    }
}
