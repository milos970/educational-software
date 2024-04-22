package com.milos.numeric.dtos;

import com.milos.numeric.validators.SchoolEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PersonalInfoDto
{
    @NotBlank
    @Size(min = 1, message = "Meno musí byť dlhé aspoň 1 znak!")
    @Size(max = 50, message = "Meno musi býť maximálne 50 znakov dlhé!")
    private String name;

    @NotBlank
    @Size(min = 1, message = "Priezvisko musí byť dlhé aspoň 1 znak!")
    @Size(max = 50, message = "Priezvisko musi býť maximálne 50 znakov dlhé!")
    private String surname;



    @Size(min = 5, max = 6, message = "Osobné číslo musí byť v rozmedzí 5 až 6 znakov!")
    private String personalNumber;

    @Email
    @SchoolEmail
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,64}$",
            message = "Heslo musí obsahovať aspon 1 neštandartný znak, jedno číslo, jeden veľký znak a musí byť v rozmedzí 8 az 64 znakov!")
    private String password;


}
