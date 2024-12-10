package ec.com.expensys.persistence.repository;

import ec.com.expensys.persistence.entity.ExpCountry;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ExpCountryRepository extends ListCrudRepository<ExpCountry, Long> {

    List<ExpCountry> findByIsDeletedFalse();

}
