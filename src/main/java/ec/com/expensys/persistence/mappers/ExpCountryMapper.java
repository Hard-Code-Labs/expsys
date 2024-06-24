package ec.com.expensys.persistence.mappers;

import ec.com.expensys.persistence.entity.ExpCountry;
import ec.com.expensys.service.dto.ExpCountryDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExpCountryMapper {

    ExpCountryDto toCountryDto(ExpCountry expCountry);

    List<ExpCountryDto> toCountries(List<ExpCountry> expCountryList);

    ExpCountry toCountry(ExpCountryDto expCountryDto);
}
