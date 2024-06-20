package ec.com.expensys.service;

import ec.com.expensys.persistence.entity.ExpRolePerson;
import ec.com.expensys.persistence.repository.ExpRolePersonRepository;

public class ExpRolePersonService {

    private final ExpRolePersonRepository expRolePersonRepository;

    public ExpRolePersonService(ExpRolePersonRepository expRolePersonRepository) {
        this.expRolePersonRepository = expRolePersonRepository;
    }

    public ExpRolePerson saveNew(ExpRolePerson expRolePerson) {
        return expRolePersonRepository.save(expRolePerson);
    }
}
