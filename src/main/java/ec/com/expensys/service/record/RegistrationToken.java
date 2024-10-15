package ec.com.expensys.service.record;

import jakarta.validation.constraints.NotBlank;

public record RegistrationToken(@NotBlank String verificationCode) { }
