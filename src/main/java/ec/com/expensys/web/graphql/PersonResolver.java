package ec.com.expensys.web.graphql;

import ec.com.expensys.dto.PersonCategoryDto;
import ec.com.expensys.dto.PersonDataDto;
import ec.com.expensys.dto.PersonDto;
import ec.com.expensys.service.ExpPersonCategoryService;
import ec.com.expensys.service.ExpPersonService;
import ec.com.expensys.service.PersonDataService;
import graphql.GraphQLContext;
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
    private final ExpPersonService expPersonService;

    @Secured("ROLE_BASIC")
    @QueryMapping(name = "findPersonByMail")
    public PersonDto findByMail(@Argument(name = "perMail") String mail, GraphQLContext context) {
        log.info("Find person by mail {}", mail);
         return expPersonService.findByMail(mail);
    }

    @QueryMapping(name = "allDataByPerMail")
    public PersonDataDto getDataByMail(@Argument(name = "perMail") String mail) {
        return personDataService.getAllDataByPerMail(mail);
    }

    @SchemaMapping(typeName = "Person", field = "categories")
    public List<PersonCategoryDto> resolveCategories(PersonDataDto personDataDto) {
        log.info("Resolving categories for person {}", personDataDto.getPerUUID());
        return personCategoryService.findCategoriesByPerson(personDataDto.getPerUUID().toString());
    }
}
