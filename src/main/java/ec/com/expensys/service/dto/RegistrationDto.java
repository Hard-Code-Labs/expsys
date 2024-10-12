package ec.com.expensys.service.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
public class RegistrationDto {

    private UUID perUUID;

    @Email(message = "Invalid mail format")
    @NotBlank(message = "Mail cannot be empty")
    private final String perMail;

    @NotBlank(message = "Name cannot be empty")
    private final String perName;

    @NotBlank(message = "Last name cannot be empty")
    private final String perLastname;

    @NotBlank(message = "Password cannot be empty")
    private final String perPassword;

    private final String perVerificationCode;
    private boolean isEnabled;

    @NotNull(message = "Country ID is required.")
    @Positive(message = "Number must be positive and greater than zero")
    private final Long countryId;
}
