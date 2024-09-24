package ec.com.expensys.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
public class RegistrationDto {
    private UUID perUUID;
    private final String perMail;
    private final String perName;
    private final String perLastname;
    private final String perPassword;
    private final String perVerificationCode;
    private boolean isEnabled;
    private final Long countryId;
}
