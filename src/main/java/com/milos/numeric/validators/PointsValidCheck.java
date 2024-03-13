package com.milos.numeric.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Map;

public class PointsValidCheck extends ABaseController implements
        ConstraintValidator<PointsValid, Map<String,String>> {


    @Override
    public void initialize(PointsValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Map<String,String> mapOfEmail,
                           ConstraintValidatorContext cvc) {
        //Implement Email Validation Login Here.
    }

    @Override
    public boolean isValid(Map<String, String> stringStringMap, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}