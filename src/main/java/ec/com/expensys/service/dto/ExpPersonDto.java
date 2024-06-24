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
public class ExpPersonDto {

    private UUID perUUID;
    private final String perMail;
    private final String perName;
    private final String perLastName;
    private final String perPassword;
    private byte[] perAvatar;
    private final Long countryId;

}
