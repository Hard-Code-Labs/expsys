package ec.com.expensys.web.controller.graphql;

import ec.com.expensys.dto.CategoryInputDto;
import ec.com.expensys.dto.PersonCategoryDto;
import ec.com.expensys.service.ExpPersonCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CategoryResolver {

    private final ExpPersonCategoryService personCategoryService;

    @MutationMapping
    public PersonCategoryDto addCategory(@Argument(name = "category") CategoryInputDto categoryInput) {
        return personCategoryService.save(categoryInput);
    }
}
