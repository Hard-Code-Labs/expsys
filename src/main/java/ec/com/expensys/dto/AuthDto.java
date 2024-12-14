package ec.com.expensys.dto;

import ec.com.expensys.validator.Trimmed;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthDto(
        @Trimmed
        @Email(message = "Invalid mail format")
        @NotBlank(message = "Mail cannot be empty")
        String username,

        @Trimmed
        @NotBlank(message = "Password cannot be empty")
        String password
) {
}
