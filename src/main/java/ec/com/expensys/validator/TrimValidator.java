package ec.com.expensys.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TrimValidator implements ConstraintValidator<Trimmed, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if(s == null){
            return true;
        }

        String trimmedValue = s.trim();
        return !trimmedValue.isEmpty() && trimmedValue.equals(s);
    }
}
