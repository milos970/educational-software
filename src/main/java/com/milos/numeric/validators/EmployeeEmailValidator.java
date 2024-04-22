package com.milos.numeric.validators;

import org.apache.tomcat.util.file.Matcher;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmployeeEmailValidator
{
    private final static String regex = "\\b[A-Za-z0-9._%+-]+@fri\\.uniza\\.sk\\b";
    private final static Pattern pattern = Pattern.compile(regex);


    public boolean isValid(String email)
    {
        return pattern.matcher(email).matches();
    }
}
