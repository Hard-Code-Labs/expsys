package ec.com.expensys.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenRequest(@NotBlank String token) { }
