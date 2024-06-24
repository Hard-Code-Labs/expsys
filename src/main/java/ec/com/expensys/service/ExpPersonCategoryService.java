package ec.com.expensys.service;

import ec.com.expensys.persistence.repository.ExpPersonCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ExpPersonCategoryService {

    private final ExpPersonCategoryRepository expPersonCategoryRepository;

    @Autowired
    public ExpPersonCategoryService(ExpPersonCategoryRepository expPersonCategoryRepository) {
        this.expPersonCategoryRepository = expPersonCategoryRepository;
    }

}
