package ec.com.expensys.service;

import ec.com.expensys.persistence.entity.ExpCountry;
import ec.com.expensys.persistence.repository.ExpCountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpCountryService {

    private final ExpCountryRepository countryRepository;

    public List<ExpCountry> findAllActive() {
        return countryRepository.findAll();
    }
}
