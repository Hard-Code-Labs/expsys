package ec.com.expensys.service;

import ec.com.expensys.persistence.entity.ExpRolePerson;
import ec.com.expensys.persistence.repository.ExpRolePersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpRolePersonService {

    private final ExpRolePersonRepository expRolePersonRepository;

    public ExpRolePerson saveNew(ExpRolePerson expRolePerson) {
        return expRolePersonRepository.save(expRolePerson);
    }
}
