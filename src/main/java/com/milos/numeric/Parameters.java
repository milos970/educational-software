package com.milos.numeric;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Parameters {
    private String expression;
    private Double tolerance;
    private Integer iterations;
    private Double min;
    private Double max;
    private Double x0;

}
