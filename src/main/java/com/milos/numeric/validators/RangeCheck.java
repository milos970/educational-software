package com.milos.numeric.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = RangeCheckValidtor.class)
public @interface RangeCheck
{
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
