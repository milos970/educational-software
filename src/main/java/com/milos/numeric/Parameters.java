package com.milos.numeric;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Parameters {
    private String function;
    private double E;
    private double a0;
    private double b0;
    private double x_1;

}
