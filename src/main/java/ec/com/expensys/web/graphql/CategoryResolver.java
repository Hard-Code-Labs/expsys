package ec.com.expensys.web.graphql;

import ec.com.expensys.dto.PersonCategoryDto;
import ec.com.expensys.service.ExpPersonCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CategoryResolver {

    private final ExpPersonCategoryService personCategoryService;


    @QueryMapping(name = "getAllCategoriesByPerson")
    public List<PersonCategoryDto> findAllByPersonId(@Argument String perUUID) {
        log.info("categories for person id {}", perUUID);
        return personCategoryService.findCategoriesByPerson(perUUID);
    }
}
