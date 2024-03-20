package com.milos.numeric.dtos;

import com.milos.numeric.validators.TeacherAvailability;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NewTeacherDto
{
    @TeacherAvailability
    private String username;
}
