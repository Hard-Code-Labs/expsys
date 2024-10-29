package ec.com.expensys.web.record;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RegisterDto(

        @Email(message = "Invalid mail format")
        @NotBlank(message = "Mail cannot be empty")
        String perMail,

        @NotBlank(message = "Name cannot be empty")
        String perName,

        @NotBlank(message = "Last name cannot be empty")
        String perLastname,

        @NotBlank(message = "Password cannot be empty")
        String perPassword,

        @NotNull(message = "Country ID is required.")
        @Positive(message = "Number must be positive and greater than zero")
        Long countryId
) {
}
