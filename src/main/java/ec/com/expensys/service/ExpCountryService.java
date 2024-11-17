package ec.com.expensys.service;

import ec.com.expensys.persistence.entity.ExpCountry;
import ec.com.expensys.persistence.repository.ExpCountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpCountryService {

    private final ExpCountryRepository countryRepository;

    public ExpCountryService(ExpCountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<ExpCountry> findAll() {
        return null;
    }
}
