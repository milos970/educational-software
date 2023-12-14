package com.milos.numeric;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTO
{
    private String expression;
    private Double tolerance;
    private Double  initialValue;
    private Double min;
    private Double max;
    private Integer iterations;
}
