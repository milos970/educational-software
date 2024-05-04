package com.milos.numeric.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class DateValidValidator implements ConstraintValidator<DateValid, String>
{

    @Override
    public void initialize(DateValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext)
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern("dd.MM.uuuu HH:mm")
                .withResolverStyle(ResolverStyle.STRICT);
        LocalDateTime receivedDate = null;
        try {
            receivedDate = LocalDateTime.parse(value, dateTimeFormatter);

        } catch (DateTimeParseException e) {

            return false;
        }

        


        String formattedString = LocalDateTime.now().format(dateTimeFormatter);

        LocalDateTime now = LocalDateTime.parse(formattedString, dateTimeFormatter);

        return receivedDate.isAfter(now);
    }
}
