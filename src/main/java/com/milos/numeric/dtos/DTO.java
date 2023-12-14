package com.milos.numeric.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTO
{
    private String expression;
    private Integer iterations;
    private Double tolerance;
    private Double lower;
    private Double upper;
    private Double initial;
}
