package ec.com.expensys.persistence.mappers;

import ec.com.expensys.persistence.entity.ExpPerson;
import ec.com.expensys.dto.PersonDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExpPersonMapper {

    //Convertir de Entity a DTO
    @Mapping(source = "expCountry.ctrId", target = "countryId")
    PersonDto toPersonDto(ExpPerson expPerson);

    List<PersonDto> toPersonsDto(List<ExpPerson> expPersons);


    //Convertir de DTO a Entity
    @InheritInverseConfiguration
    @Mapping(target = "perId", ignore = true)
    @Mapping(target = "lastAccess", ignore = true)
    @Mapping(target = "perVerificationCode", ignore = true)
    @Mapping(target = "perPassword", ignore = true)
    @Mapping(target = "roleList", ignore = true)
    @Mapping(target = "expCategories", ignore = true)
    @Mapping(target = "expTransactions", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    ExpPerson toPerson(PersonDto personDto);
}


