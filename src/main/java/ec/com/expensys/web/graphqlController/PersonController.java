package ec.com.expensys.web.graphqlController;

import ec.com.expensys.dto.PersonDto;
import ec.com.expensys.service.ExpPersonService;
import graphql.GraphQLContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.graphql.data.method.annotation.QueryMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PersonController {

    private final ExpPersonService expPersonService;

    @Secured("ROLE_BASIC")
    @QueryMapping(name = "findPersonByMail")
    public PersonDto findByMail(@Argument(name = "perMail") String mail, GraphQLContext context) {
        log.info("Find person by mail {}", mail);
         return expPersonService.findByMail(mail);
    }
}
