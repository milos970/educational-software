package com.milos.numeric.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class MaterialSizeValidator implements ConstraintValidator<MaterialSizeValid, MultipartFile>
{
    private final static Long FILE_SIZE_LIMIT = (long) (5 * 1024 * 1024);
    @Override
    public void initialize(MaterialSizeValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext)
    {
        return file.getSize() <= FILE_SIZE_LIMIT;
    }
}
