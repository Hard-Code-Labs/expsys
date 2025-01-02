package ec.com.expensys.persistence.repository;

import ec.com.expensys.persistence.entity.ExpPersonCategory;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ExpPersonCategoryRepository extends ListCrudRepository<ExpPersonCategory, Long> {

    List<ExpPersonCategory> findAllByExpPerson_PerId(Long expPersonPerId);
}
