package ec.com.expensys.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExpCountryDto {

    private final Long ctrId;
    private final String ctrAcronym;
    private final String ctrName;
    private String ctrIcon;
}
