package ec.com.expensys.persistence.mappers;

import ec.com.expensys.dto.PersonDataDto;
import ec.com.expensys.persistence.entity.ExpPerson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExpPersonMapper {

    @Mapping(target = "categories", ignore = true)
    PersonDataDto toPersonDataDto(ExpPerson expPerson);
}


