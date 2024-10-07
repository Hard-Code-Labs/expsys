package ec.com.expensys.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
public class RegistrationDto {

    private UUID perUUID;

    @NotNull(message = "Mail can not be null.")
    @Size(min = 5,max = 99, message = "Mail can not be empty and must not exceed 99 characters")
    private final String perMail;

    @NotNull(message = "Name can not be null.")
    @Size(min = 2,max = 60, message = "Name can not be empty and must not exceed 60 characters")
    private final String perName;

    @NotNull(message = "Lastname can not be null.")
    @Size(min = 2,max = 99, message = "Lastname can not be empty and must not exceed 60 characters")
    private final String perLastname;

    @NotNull(message = "Password is required.")
    @Size(min = 8, message = "Password can not be empty.")
    private final String perPassword;

    private final String perVerificationCode;
    private boolean isEnabled;

    @NotNull(message = "Country ID is required.")
    private final Long countryId;
}
