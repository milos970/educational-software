package com.milos.numeric.validators;


import com.milos.numeric.dtos.DTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RangeCheckValidtor implements ConstraintValidator<RangeCheck, DTO>
{
    @Override
    public void initialize(RangeCheck date)
    {

    }

    @Override
    public boolean isValid(DTO dto, ConstraintValidatorContext constraintValidatorContext)
    {
            return false;
    }
}
