package com.milos.numeric.security;

import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PasswordGenerator
{
    private final Random random;

    public PasswordGenerator()
    {
        this.random = new Random();
    }


    public String generate()
    {
        StringBuilder sb = new StringBuilder();

        final int passwordLength = 8;

        for (int i = 0; i < passwordLength - 3; ++i)
        {
            char lowChar = (char) random.nextInt(97,123);
            sb.append(lowChar);
        }

        char upChar = (char) random.nextInt(65,91);
        sb.append(upChar);

        char ch = (char) random.nextInt(58,65);
        sb.append(ch);

        int number = random.nextInt(0,10);
        sb.append(number);

        return sb.toString();
    }
}
