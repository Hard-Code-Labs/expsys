package ec.com.expensys.dto;

import lombok.Builder;

@Builder
public record PersonCategoryDto(
        Long catId,
        String catName,
        String catType,
        String catIcon,
        Boolean isDeleted,
        PersonDataDto person
) {}
