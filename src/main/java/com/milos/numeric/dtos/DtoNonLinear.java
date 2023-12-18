package com.milos.numeric.dtos;

import com.milos.numeric.validations.NonLinear;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoNonLinear
{
    @NonLinear
    private String expression;

    @Size(min = 1, max = Integer.MAX_VALUE)
    private int iterations;


    private double tolerance;

    private double lower;

    private double upper;

    private double initial;
}
