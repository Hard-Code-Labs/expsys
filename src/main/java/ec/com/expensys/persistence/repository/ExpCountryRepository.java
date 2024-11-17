package ec.com.expensys.persistence.repository;

import ec.com.expensys.persistence.entity.ExpCountry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ExpCountryRepository extends CrudRepository<ExpCountry, Long> {

    Optional<List<ExpCountry>> findAllAndIsDeletedFalse();
}
