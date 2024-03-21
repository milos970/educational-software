package com.milos.numeric.dtos;

import com.milos.numeric.validators.EmployeeEmail;
import com.milos.numeric.validators.EmployeePersonalNumber;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PersonalInfoDto
{
    @EmployeePersonalNumber
    private String personalNumber;

    @Size(min = 1, max = 50)
    private String name;

    @Size(min = 1, max = 50)
    private String surname;

    @EmployeeEmail
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{12,}$",
            message = "password must contain at least 1 uppercase, 1 lowercase, 1 special character and 1 digit")

    private String password;


}
