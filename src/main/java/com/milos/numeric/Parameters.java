package com.milos.numeric;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Parameters {
    private String expression;
    private double tolerance;
    private int iterations;
    private double min;
    private double max;
    private double x0;

}
