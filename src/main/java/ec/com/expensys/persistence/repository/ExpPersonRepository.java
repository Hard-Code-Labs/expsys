package ec.com.expensys.persistence.repository;

import ec.com.expensys.persistence.entity.ExpPerson;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface ExpPersonRepository extends ListCrudRepository<ExpPerson, Long> {

    ExpPerson findByPerMail(String perMail);
    Optional<ExpPerson> findByPerVerificationCode(String perVerificationCode);

}
