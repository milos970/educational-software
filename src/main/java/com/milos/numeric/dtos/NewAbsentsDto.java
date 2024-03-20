package com.milos.numeric.dtos;

import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewAbsentsDto
{
    @Max(13)
    private int absents;
}
