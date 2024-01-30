package com.milos.numeric.validators;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NonLinearFunctionCheckValidator.class)
@Documented
public @interface NonLinearCheck {
    String message() default "Expression must be a linear function!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
