package com.milos.numeric.validators;

import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.services.PersonalInfoService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class PasswordValidValidator implements ConstraintValidator<PasswordValid, String>
{
    @Autowired
    private final PersonalInfoService personalInfoService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public PasswordValidValidator(PersonalInfoService personalInfoService, PasswordEncoder passwordEncoder) {
        this.personalInfoService = personalInfoService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void initialize(PasswordValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String oldPassword, ConstraintValidatorContext constraintValidatorContext)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<PersonalInfo> optionalPersonalInfo = this.personalInfoService.findByUsername(username);

        if (optionalPersonalInfo.isEmpty())
        {
            return false;
        }

        PersonalInfo personalInfo = optionalPersonalInfo.get();
        String currentPassword = personalInfo.getPassword();

        return passwordEncoder.matches(oldPassword, currentPassword);
    }
}

