package ec.com.expensys.service;

import ec.com.expensys.dto.CountryDto;
import ec.com.expensys.persistence.mappers.ExpCountryMapper;
import ec.com.expensys.persistence.repository.ExpCountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpCountryService {

    private final ExpCountryRepository countryRepository;
    private final ExpCountryMapper countryMapper;

    public List<CountryDto> findAllActive() {
        return countryMapper.toCountriesDto(countryRepository.findByIsDeletedFalse());
    }
}
