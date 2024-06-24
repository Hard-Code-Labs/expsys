package ec.com.expensys.persistence.repository;

import ec.com.expensys.persistence.entity.ExpPerson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface ExpPersonRepository extends ListCrudRepository<ExpPerson, Long> {

    @Query(value = "SELECT p FROM ExpPerson p WHERE p.perMail =:mail")
    boolean existsByMail(@Param("mail") String mail);

    ExpPerson findByPerMail(String perMail);

}
