package ec.com.expensys.web.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ExpPersonDto {

    @NonNull
    private String perMail;

    @NonNull
    private String perName;

    @NonNull
    private String perLastName;

    @NonNull
    private String perPassword;

    @NonNull
    private String countryId;

}
