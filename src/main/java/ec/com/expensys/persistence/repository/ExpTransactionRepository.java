package ec.com.expensys.persistence.repository;

import ec.com.expensys.persistence.entity.ExpTransaction;
import org.springframework.data.repository.CrudRepository;

public interface ExpTransactionRepository extends CrudRepository<ExpTransaction, Long> {
}
