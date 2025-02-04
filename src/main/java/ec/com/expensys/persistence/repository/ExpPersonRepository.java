package ec.com.expensys.persistence.repository;

import ec.com.expensys.persistence.entity.ExpPerson;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ExpPersonRepository extends ListCrudRepository<ExpPerson, Long> {

    Optional<ExpPerson> findByPerVerificationCode(String perVerificationCode);

    Optional<ExpPerson> findByPerMail(String mail);

    Optional<ExpPerson> findByPerUUID(UUID perUUID);

    //Procedimiento almacenado que crea categorias cuando un usuario se da de alta
    @Procedure(value = "exp.createCategoriesByNewPerson")
    void createCategoriesByNewPerson(@Param("p_perId") Long perId);
}
