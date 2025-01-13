package ec.com.expensys.persistence.repository;

import ec.com.expensys.persistence.entity.ExpPersonCategory;
import org.springframework.data.domain.*;
import org.springframework.data.repository.ListCrudRepository;

public interface ExpPersonCategoryRepository extends ListCrudRepository<ExpPersonCategory, Long> {

    Page<ExpPersonCategory> findAllByExpPerson_PerId(Long expPersonPerId, Pageable pageable);
}
