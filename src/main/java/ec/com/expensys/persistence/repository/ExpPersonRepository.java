package ec.com.expensys.persistence.repository;

import ec.com.expensys.persistence.entity.ExpPerson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ExpPersonRepository extends ListCrudRepository<ExpPerson, Long> {

    ExpPerson findByPerMail(String perMail);

    Optional<ExpPerson> findByPerUUID(UUID perUUID);

    Optional<ExpPerson> findByPerVerificationCode(String perVerificationCode);

    @Query("SELECT p FROM ExpPerson p WHERE p.perMail =:mail AND p.isEnabled = true AND p.isDeleted = false")
    Optional<ExpPerson> findActiveByMail(String mail);

    //Procedimiento almacenado que crea categorias cuando un usuario se da de alta
    @Procedure(value = "exp.createCategoriesByNewPerson")
    void createCategoriesByNewPerson(@Param("p_perId") Long perId);

}
