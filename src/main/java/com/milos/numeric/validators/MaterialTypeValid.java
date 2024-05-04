package com.milos.numeric.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = MaterialTypeValidator.class)
public @interface MaterialTypeValid
{
    String message() default "Filetype has to be one of following types: 'application/pdf',        // PDF\n" +
            "        'application/msword',     // DOC\n" +
            "        'application/vnd.openxmlformats-officedocument.wordprocessingml.document', // DOCX\n" +
            "        'image/png',              // PNG\n" +
            "        'image/jpeg',             // JPEG\n" +
            "        'image/jpg',              // JPG\n" +
            "        'text/csv',               // CSV\n" +
            "        'application/vnd.ms-excel', // CSV alternative (Excel)\n" +
            "        'application/vnd.ms-powerpoint', // PPTX\n" +
            "        'application/vnd.openxmlformats-officedocument.presentationml.presentation'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
