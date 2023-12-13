package com.milos.numeric;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTO
{
    private String expression;
    private double tolerance;
    private double  initialValue;
    private double min;
    private double max;
    private int iterations;
}
