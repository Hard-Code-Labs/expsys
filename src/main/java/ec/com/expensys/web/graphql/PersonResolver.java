package ec.com.expensys.web.graphql;

import ec.com.expensys.dto.PersonCategoryDto;
import ec.com.expensys.dto.PersonDataDto;
import ec.com.expensys.service.ExpPersonCategoryService;
import ec.com.expensys.service.PersonDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class PersonResolver {

    private final ExpPersonCategoryService personCategoryService;
    private final PersonDataService personDataService;

    @Secured("ROLE_BASIC")
    @QueryMapping(name = "personData")
    public PersonDataDto personDataByMail(@Argument(name = "perMail") String mail) {
        return personDataService.getAllDataByPerMail(mail);
    }

    @SchemaMapping(typeName = "Person", field = "categories")
    public List<PersonCategoryDto> resolveCategories(PersonDataDto personDataDto) {
        log.info("Resolving categories for person {}", personDataDto.getPerUUID());
        return personCategoryService.findCategoriesByPerson(personDataDto.getPerUUID().toString());
    }
}
