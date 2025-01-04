package ec.com.expensys.dto;

import ec.com.expensys.handler.Trimmed;
import jakarta.validation.constraints.*;

public record RegisterDto(

        @Trimmed
        @Email(message = "Invalid mail format")
        @NotBlank(message = "Mail cannot be empty")
        @Size(max = 100, message = "Max 100 characters for mail")
        String perMail,

        @Trimmed
        @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
        @NotBlank(message = "Name cannot be empty")
        String perName,

        @Trimmed
        @NotBlank(message = "Last name cannot be empty")
        @Size(min = 3, max = 30, message = "Lastname must be between 3 and 30 characters")
        String perLastname,

        @Trimmed
        @NotBlank(message = "Password cannot be empty")
        String perPassword,

        @NotNull(message = "Country ID is required.")
        @Positive(message = "Number must be positive and greater than zero")
        Long countryId
) { }
