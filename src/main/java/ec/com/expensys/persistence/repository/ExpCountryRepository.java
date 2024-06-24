package ec.com.expensys.persistence.repository;

import ec.com.expensys.persistence.entity.ExpCountry;
import org.springframework.data.repository.CrudRepository;

public interface ExpCountryRepository extends CrudRepository<ExpCountry, Long> {
}
