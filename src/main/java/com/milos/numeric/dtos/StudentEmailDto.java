package com.milos.numeric.dtos;

import com.milos.numeric.validators.StudentEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentEmailDto
{
    @StudentEmail
    @Email
    private String email;
}
