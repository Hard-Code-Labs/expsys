package ec.com.expensys.persistence.mappers;

import ec.com.expensys.dto.CategoryInputDto;
import ec.com.expensys.dto.PersonCategoryDto;
import ec.com.expensys.persistence.entity.ExpPersonCategory;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExpPersonCategoryMapper {

    @InheritInverseConfiguration
    @Mapping(target = "expPerson", ignore = true)
    ExpPersonCategory toExpPersonCategory(CategoryInputDto categoryInput);

    PersonCategoryDto toExpPersonCategoryDto(ExpPersonCategory personCategory);
}
