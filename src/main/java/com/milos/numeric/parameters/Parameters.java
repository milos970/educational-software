package com.milos.numeric.parameters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Parameters
{
    private String expression;

    private String der;
    private Double tolerance;
    private Integer iterations;
    private Double lower;
    private Double upper;
    private Double initialValue;

    private Integer intervals;

}
