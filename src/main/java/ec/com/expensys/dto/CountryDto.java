package ec.com.expensys.dto;

public record CountryDto(
        Long ctrId,
        String ctrAcronym,
        String ctrName,
        String ctrIcon) { }
