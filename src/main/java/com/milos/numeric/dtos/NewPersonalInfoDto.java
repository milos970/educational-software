package com.milos.numeric.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class NewPersonalInfoDto
{
    @NotBlank
    private String personalNumber;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @Email
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{12,}$",
            message = "password must contain at least 1 uppercase, 1 lowercase, 1 special character and 1 digit")
    private String password;


}
