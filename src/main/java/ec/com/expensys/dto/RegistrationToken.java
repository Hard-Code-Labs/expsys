package ec.com.expensys.dto;

import jakarta.validation.constraints.NotBlank;

public record RegistrationToken(@NotBlank String verificationCode) { }
