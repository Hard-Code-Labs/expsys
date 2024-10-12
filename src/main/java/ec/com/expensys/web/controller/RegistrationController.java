package ec.com.expensys.web.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import ec.com.expensys.config.security.JWTUtils;
import ec.com.expensys.persistence.entity.ExpPerson;
import ec.com.expensys.service.ExpPersonService;
import ec.com.expensys.service.dto.ExpPersonDto;
import ec.com.expensys.service.dto.RegistrationDto;
import ec.com.expensys.web.exception.ErrorCode;
import ec.com.expensys.web.exception.ExpiredTokenException;
import ec.com.expensys.web.exception.NotFoundException;
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
    private final JWTUtils jwtUtils;

    public RegistrationController(ExpPersonService personService, JWTUtils jwtUtils) {
        this.personService = personService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("")
    public ResponseEntity<ExpPersonDto> register(@Valid @RequestBody RegistrationDto personDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.registerNewPerson(personDto));
    }

    //revisar
    @PostMapping(path = "/confirmation")
    public ResponseEntity<?> confirmRegistration(@RequestBody String token) {

        ExpPerson person = personService.findByPerVerificationCode(token)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND.getCode(),
                        "User has been removed from the database. Please register again",
                        RegistrationController.class.getName(),
                        false));

        if(person.getIsEnabled()){
            return ResponseEntity.ok("User already registered. Please redirect to login page");
        }else{
            try {
                jwtUtils.validateToken(token);
            } catch (TokenExpiredException e) {

                personService.deletePerson(person);
                throw new ExpiredTokenException("Token has expired. Please register again", JWTUtils.class.getName());
            }
        }

        personService.newPersonEnabled(person);
        return ResponseEntity.ok("User registration successfully confirmed");

    }
}
