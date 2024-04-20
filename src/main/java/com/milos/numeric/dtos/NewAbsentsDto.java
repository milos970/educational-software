package com.milos.numeric.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewAbsentsDto
{
    @Max(13)
    @Min(1)
    private int absents;
}
