package ec.com.expensys.persistence.repository;

import ec.com.expensys.persistence.entity.ExpPerson;
import org.springframework.data.repository.CrudRepository;

public interface ExpPersonRepository extends CrudRepository<ExpPerson, Integer> {
}
