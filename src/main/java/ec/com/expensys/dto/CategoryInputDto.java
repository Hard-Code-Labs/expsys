package ec.com.expensys.dto;

import ec.com.expensys.web.utils.TypeCategoryEnum;

public record CategoryInputDto(String perUUID,
                               String catName,
                               TypeCategoryEnum catType,
                               String catIcon) {}
