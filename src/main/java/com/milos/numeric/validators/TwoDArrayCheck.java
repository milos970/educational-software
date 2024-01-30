package com.milos.numeric.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NonLinearFunctionCheckValidator.class)
@Documented
public @interface TwoDArrayCheck
{
    String message() default "Expression must be 2d array!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
