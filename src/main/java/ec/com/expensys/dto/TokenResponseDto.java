package ec.com.expensys.dto;

import lombok.NonNull;

public record TokenResponseDto(@NonNull String accessToken, @NonNull String refreshToken) {}
