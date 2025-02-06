package ec.com.expensys.dto;

import jakarta.validation.constraints.NotBlank;

public record OneTimeToken(@NotBlank String token) { }
