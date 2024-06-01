package ec.com.expensys.service;

import ec.com.expensys.persistence.repository.ExpPersonCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpPersonCategory {

    private final ExpPersonCategoryRepository expPersonCategoryRepository;

    @Autowired
    public ExpPersonCategory(ExpPersonCategoryRepository expPersonCategoryRepository) {
        this.expPersonCategoryRepository = expPersonCategoryRepository;
    }

    public List<ExpPersonCategory> findAll() {
        return expPersonCategoryRepository.findAll();
    }
}
