package ec.com.expensys.web.controller;

import ec.com.expensys.service.ExpPersonCategoryService;
import ec.com.expensys.service.ExpPersonService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class ExpPersonCategoryController {

    private final ExpPersonCategoryService expPersonCategoryService;
    private final ExpPersonService expPersonService;

    public ExpPersonCategoryController(ExpPersonCategoryService expPersonCategoryService,
                                       ExpPersonService expPersonService) {

        this.expPersonCategoryService = expPersonCategoryService;
        this.expPersonService = expPersonService;
    }

    //TODO reestructurar las tablas de la base de datos para apuntar al esquema EXP
//    @GetMapping("/mycategories/{uuidPerson}")
//    public ResponseEntity<List<ExpPersonCategory>> findAllByPersonAndCommon(@PathVariable String uuidPerson) {
//        System.out.println("uuid: " + uuidPerson);
//        ExpPerson person = expPersonService.findByUuid(UUID.fromString(uuidPerson));
//        System.out.println("person: " + person.getPerLastname());
//        return ResponseEntity.ok(expPersonCategoryService.findAllCommonsAndByPerson(person));
//    }

}
