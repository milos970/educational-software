package com.milos.numeric.dtos;

import com.milos.numeric.validators.StudentEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewStudentDto
{
    @StudentEmail
    @Email
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{12,}$",
            message = "password must contain at least 1 uppercase, 1 lowercase, 1 special character and 1 digit")
    private String password;
}
