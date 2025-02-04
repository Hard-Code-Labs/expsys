package ec.com.expensys.persistence.mappers;

import ec.com.expensys.dto.CountryDto;
import ec.com.expensys.persistence.entity.ExpCountry;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExpCountryMapper {

    List<CountryDto> toCountriesDto(List<ExpCountry> expCountries);
}
