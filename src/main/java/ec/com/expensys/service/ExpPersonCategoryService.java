package ec.com.expensys.service;

import ec.com.expensys.persistence.repository.ExpPersonCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ExpPersonCategoryService {

    private final ExpPersonCategoryRepository expPersonCategoryRepository;

}
