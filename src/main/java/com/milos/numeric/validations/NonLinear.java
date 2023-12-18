package com.milos.numeric.validations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PrefixValidator.class)
@Documented
public @interface NonLinear {
    String message() default "Expression must be a linear function!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
