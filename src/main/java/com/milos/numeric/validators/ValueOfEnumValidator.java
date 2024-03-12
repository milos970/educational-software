package com.milos.numeric.validators;

import com.milos.numeric.Authority;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnumAuthority, Authority>
{
    private Authority[] values;

    @Override
    public void initialize(ValueOfEnumAuthority constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        this.values = constraintAnnotation.anyOf();

    }

    @Override
    public boolean isValid(Authority value, ConstraintValidatorContext constraintValidatorContext)
    {
        return value == null || Arrays.asList(values).contains(value);
    }
}
