package ec.com.expensys.handler;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TrimValidator implements ConstraintValidator<Trimmed, String> {

    private static final String VALID_PATTERN = "^\\S+$";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if (s == null) {
            return true;
        }

        return s.matches(VALID_PATTERN);
    }
}
