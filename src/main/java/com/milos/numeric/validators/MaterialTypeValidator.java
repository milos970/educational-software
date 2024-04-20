package com.milos.numeric.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

public class MaterialTypeValidator implements ConstraintValidator<MaterialTypeValid, MultipartFile>
{
    private final Set<String> mimeTypes = new HashSet<>();

    @Override
    public void initialize(MaterialTypeValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.mimeTypes.add("application/pdf");
        this.mimeTypes.add("application/msword");
        this.mimeTypes.add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        this.mimeTypes.add("image/png");
        this.mimeTypes.add("image/jpeg");
        this.mimeTypes.add("image/jpg");
        this.mimeTypes.add("text/csv");
        this.mimeTypes.add("application/vnd.ms-excel");
        this.mimeTypes.add("application/vnd.ms-powerpoint");
        this.mimeTypes.add("application/vnd.openxmlformats-officedocument.presentationml.presentation");
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext)
    {
        return this.mimeTypes.contains(file.getContentType());
    }
}
