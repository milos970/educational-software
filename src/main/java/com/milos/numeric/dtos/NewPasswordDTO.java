package com.milos.numeric.dtos;


import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPasswordDTO
{
    private String oldPassword;

    private String newPassword;

    private String retypedNewPassword;
}
