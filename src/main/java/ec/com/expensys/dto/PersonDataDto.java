package ec.com.expensys.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
public class PersonDataDto {

    private UUID perUUID;
    private final String perMail;
    private final String perName;
    private final String perLastname;
    private boolean isEnabled;
    private byte[] perAvatar;
    private List<PersonCategoryDto> categories;

}
