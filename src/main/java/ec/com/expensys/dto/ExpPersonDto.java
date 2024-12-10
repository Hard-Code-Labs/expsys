package ec.com.expensys.dto;

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
    private final String perLastname;
    private boolean isEnabled;
    private byte[] perAvatar;
    private final Long countryId;

}
