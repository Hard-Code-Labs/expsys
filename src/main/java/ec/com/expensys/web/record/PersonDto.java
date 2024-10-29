package ec.com.expensys.web.record;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PersonDto(
        UUID perUUID,
        String perMail,
        String perName,
        String perLastname,
        boolean isEnabled,
        byte[] perAvatar,
        Long countryId
) { }
