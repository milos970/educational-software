package com.milos.numeric.validators;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CsvValidator
{
    private final static String MIME = "text/csv";


    public boolean isValid(String email)
    {
        return email.equals(MIME);
    }
}

