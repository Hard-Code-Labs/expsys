package ec.com.expensys.web.controller;

import ec.com.expensys.persistence.entity.ExpPerson;
import ec.com.expensys.service.ExpPersonService;
import ec.com.expensys.service.dto.ExpPersonDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class ExpPersonController {

    private final ExpPersonService expPersonService;

    public ExpPersonController(ExpPersonService expPersonService) {
        this.expPersonService = expPersonService;
    }



    @PostMapping("/registration")
    public ResponseEntity<ExpPerson> register(@RequestBody ExpPersonDto personDto){
        try{
            return new ResponseEntity<>(
                    expPersonService.registerNewPerson(personDto),
                    HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<ExpPersonDto>> findAll(){
         return ResponseEntity.ok(expPersonService.findAll());
    }
}
