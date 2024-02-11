package com.milos.numeric.validators;

import com.milos.numeric.dtos.NewPasswordDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NewPasswordDTOValidator implements Validator
{
    private static final String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{12,}$";
    private static final Pattern pattern = Pattern.compile(regex);



    @Override
    public boolean supports(Class<?> clazz) {
        return NewPasswordDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        NewPasswordDTO object = (NewPasswordDTO) target;

        if ( !object.getNewPassword().equals(object.getRetypedNewPassword()))
        {
            errors.rejectValue("retypedNewPassoword", "Passwords do not match!");
        }

        Matcher matcher = pattern.matcher(object.getNewPassword());

        if ( !matcher.find())
        {
            errors.rejectValue("newPassword", "Password does not meet requirements!");
        }


    }
}
