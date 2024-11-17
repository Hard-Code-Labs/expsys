package ec.com.expensys.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.Set;

public class RegisterDtoValidationTest {

    private Validator validator;

    @BeforeEach
    void init(){
        ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .buildValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void invalidEmailTest() {
        RegisterDto invalidDto = new RegisterDto(
                "invalid-email.com",
                "validName",
                "validLastName",
                "password123",
                1L
        );

        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(invalidDto);

        assertEquals(1, violations.size());
        assertEquals("Invalid mail format", violations.iterator().next().getMessage());
    }


    @Test
    void blankSpaceAtInitOrEndTest() {
        RegisterDto invalidDto = new RegisterDto(
                "valid@email.com",
                " invalid Name ",
                "validLastName",
                "password123",
                1L
        );

        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(invalidDto);

        assertEquals(1, violations.size());
        assertEquals("Field cannot start or end with a white space", violations.iterator().next().getMessage());
    }

    @Test
    void nameLessThan3CharTest() {
        RegisterDto invalidDto = new RegisterDto(
                "valid@email.com",
                "na",
                "validLastName",
                "password123",
                1L
        );

        Set<ConstraintViolation<RegisterDto>> violations = validator.validate(invalidDto);

        assertEquals("Name must be between 3 and 30 characters", violations.iterator().next().getMessage());
    }
}
