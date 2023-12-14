package com.milos.numeric.parameters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Parameters
{
    private String expression;
    private double tolerance;
    private int iterations;
    private double lower;
    private double upper;
    private double initial;

}
