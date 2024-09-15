package com.example.demo;

import com.milos.numeric.dtos.NewPasswordDto;
import com.milos.numeric.services.methods.NewtonMethod;
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
    public void testNewtonMethod()
    {
        double res  = Double.valueOf(NewtonMethod.newtonMethod("10cos(x - 1) - x ^2 + 2x -1", 0.001,2.4).getLast()[0]);
        assertEquals(2.379,res,0.001);

        res = Double.valueOf(NewtonMethod.newtonMethod("x^2 - 4", 0.001,1.5).getLast()[0]);
        assertEquals(2,res,0.001);

        res = Double.valueOf(NewtonMethod.newtonMethod("sin(x)", 0.01,3).getLast()[0]);
        assertEquals(3.14,res,0.01);

        res = Double.valueOf(NewtonMethod.newtonMethod("e^x - 2", 0.001,0.5).getLast()[0]);
        assertEquals(0.6931,res,0.001);

        res = Double.valueOf(NewtonMethod.newtonMethod("x^2 - 4", 0.001,1.5).getLast()[0]);
        assertEquals(2,res,0.001);

        res = Double.valueOf(NewtonMethod.newtonMethod("x^3 + x - 1", 0.001,0.7).getLast()[0]);
        assertEquals(0.6823,res,0.001);

        res = Double.valueOf(NewtonMethod.newtonMethod("cos(x) - x", 0.001,0.5).getLast()[0]);
        assertEquals(0.7391,res,0.001);
    }

    @Test
    public void testRegulaFalsiMethod()
    {

        assertEquals(2.379, NewtonMethod.regulaFalsi("10cos(x - 1) - x ^2 + 2x -1", 0.001, 2.3, 2.4), 0.001);
        assertEquals(0.6931, NewtonMethod.regulaFalsi("e^x - 2", 0.0001, 0, 1), 0.0001);
        assertEquals(3.1416, NewtonMethod.regulaFalsi("sin(x)", 0.00001, 3.0, 4.0), 0.00001);
        assertEquals(1.3247, NewtonMethod.regulaFalsi("x^3 - x - 1", 0.0001, 1.0, 2.0), 0.0001);
        assertEquals(Double.NaN, NewtonMethod.regulaFalsi("lg(x)", 0.00001, 2.0, 3.0), 0.00001);
        assertEquals(0.7391, NewtonMethod.regulaFalsi("cos(x) - x", 0.0001, 0, 1.0), 0.0001);

    }

    @Test
    public void testBisectionMethod()
    {

        assertEquals(2.0, NewtonMethod.bisection("x^2 - 4", 0.001, 1.0, 3.0), 0.001);
        assertEquals(0.6931, NewtonMethod.bisection("e^x - 2", 0.0001, 0, 1), 0.0001);
        assertEquals(3.1416, NewtonMethod.bisection("sin(x)", 0.00001, 3.0, 4.0), 0.00001);
        assertEquals(1.3247, NewtonMethod.bisection("x^3 - x - 1", 0.0001, 1.0, 2.0), 0.0001);
        assertEquals(Double.NaN, NewtonMethod.bisection("lg(x)", 0.001, 2.0, 3.0), 0.00001);
        assertEquals(0.7391, NewtonMethod.bisection("cos(x) - x", 0.0001, 0, 1.0), 0.0001);
    }

    @Test
    public void testValidNewPasswordDto() {




        /*NewtonMethod.newtonMethod("10cos(x - 1) - x ^2 + 2x -1", 0.0001, 5);

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
        assertEquals(0, violations.size());*/


    }


}