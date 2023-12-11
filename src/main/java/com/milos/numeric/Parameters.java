package com.milos.numeric;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Parameters {
    private String function;
    private double tolerance;
    private int iterations;
    private double min;
    private double max;
    private double x0;

}
