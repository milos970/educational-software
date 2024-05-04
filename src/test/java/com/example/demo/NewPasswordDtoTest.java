package com.example.demo;

import com.milos.numeric.dtos.NewPasswordDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NewPasswordDtoTest {



    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void testValidNewPasswordDto() {
        NewPasswordDto newPasswordDto = new NewPasswordDto();
        newPasswordDto.setOldPassword("oldPassword");


        Set<ConstraintViolation<NewPasswordDto>> violations = validator.validate(newPasswordDto);

        newPasswordDto.setNewPassword("Heslisko123'");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());


        newPasswordDto.setNewPassword("Heslisko123\"");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123-");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());


        newPasswordDto.setNewPassword("Heslisko123_");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123-");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123:");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123;");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123?");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123!");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123/");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123\\");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123.");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123*");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123^");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123~");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123,");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123/");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123+");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123<");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123>");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123(");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123)");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());


        newPasswordDto.setNewPassword("Heslisko123[");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());

        newPasswordDto.setNewPassword("Heslisko123]");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());


        newPasswordDto.setNewPassword("Heslisko123{");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());


        newPasswordDto.setNewPassword("Heslisko123}");
        violations = validator.validate(newPasswordDto);
        assertEquals(0, violations.size());


    }


}