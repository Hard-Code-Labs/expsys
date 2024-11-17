package ec.com.expensys.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {TrimValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Trimmed {

    String message() default "Field cannot start or end with a white space";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
