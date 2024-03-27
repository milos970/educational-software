package com.milos.numeric;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

@Component
public class DateParser
{
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter
            .ofPattern("dd.MM.uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    public LocalDateTime parseStringToLocalDate(String localDateTime)
    {
        return LocalDateTime.parse(localDateTime, dateTimeFormatter);
    }

    public String parseLocalDateToString(LocalDateTime localDateTime)
    {
        return localDateTime.format(dateTimeFormatter);
    }


    public LocalDateTime formatLocalDateInFormat(LocalDateTime localDateTime)
    {
        String string = localDateTime.format(dateTimeFormatter);
        return LocalDateTime.parse(string, dateTimeFormatter);
    }
}
