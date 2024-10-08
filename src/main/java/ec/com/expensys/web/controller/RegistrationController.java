package ec.com.expensys.web.controller;

import ec.com.expensys.persistence.entity.ExpPerson;
import ec.com.expensys.service.ExpPersonService;
import ec.com.expensys.service.dto.ExpPersonDto;
import ec.com.expensys.service.dto.RegistrationDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/register")
public class RegistrationController {

    private final ExpPersonService personService;

    public RegistrationController(ExpPersonService personService) {
        this.personService = personService;
    }

    @PostMapping("")
    public ResponseEntity<ExpPersonDto> register(@Valid @RequestBody RegistrationDto personDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.registerNewPerson(personDto));
    }

    @PostMapping(path = "/confirmation")
    public ResponseEntity<Boolean> confirmRegistration(@RequestBody String token){
        try{
            ExpPerson person = personService.findByPerVerificationCode(token);

            if(person.getPerVerificationCode().equals(token)){
                if(personService.verifyToken(token)){
                    personService.enablePerson(person);
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }else{
                    log.error("El token ha caducado");
                    return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }


}
