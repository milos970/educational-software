package com.milos.numeric.dtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTO {

    private String expression;

    @Size(min = 1, message = "At least 1 iteration is required)")
    private Integer iterations;

    @DecimalMin(value = "0.000000000001",  message = "Value must be greater than 0.000000000001!")
    @DecimalMax(value = "0.1", message = "Value must be less than 0.1!")
    private Double tolerance;

    private Double lower;

    private Double upper;

    private Double initial;
}
