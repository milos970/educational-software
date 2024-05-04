package com.milos.numeric.dtos;


import com.milos.numeric.validators.PasswordValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPasswordDto
{


    private String oldPassword;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!\\\\@%.#&\\-()\\[\\]\\-_{}\\]:;'\",?/*~$^+=<>]).{8,64}$",
            message = "password must contain at least 1 uppercase, 1 lowercase, 1 special character and 1 digit")
    private String newPassword;

}
