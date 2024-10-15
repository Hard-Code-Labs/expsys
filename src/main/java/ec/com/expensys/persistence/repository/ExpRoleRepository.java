package ec.com.expensys.persistence.repository;

import ec.com.expensys.persistence.entity.ExpRole;
import ec.com.expensys.web.utils.RoleEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ExpRoleRepository extends CrudRepository<ExpRole, Long> {

    Optional<ExpRole> findByRolName(RoleEnum rolName);
}
