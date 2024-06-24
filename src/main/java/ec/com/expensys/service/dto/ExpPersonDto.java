package ec.com.expensys.service.dto;

import ec.com.expensys.persistence.entity.ExpCountry;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ExpPersonDto {

    private UUID perUUID;
    private final String perMail;
    private final String perName;
    private final String perLastName;
    private final String perPassword;
    private byte[] perAvatar;
    private Long countryId;

}
