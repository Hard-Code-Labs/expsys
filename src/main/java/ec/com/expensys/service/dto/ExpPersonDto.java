package ec.com.expensys.web.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ExpPersonDto {

    private UUID perUUID;
    private String perMail;

    private String perName;

    private String perLastName;

    private String perPassword;

    private String countryId;

}
