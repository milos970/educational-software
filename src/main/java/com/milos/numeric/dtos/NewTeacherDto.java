package com.milos.numeric.dtos;

import com.milos.numeric.validators.EmployeeAvailability;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NewTeacherDto
{
    @EmployeeAvailability
    private String username;
}
