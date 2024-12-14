package ec.com.expensys.web.controller;

import ec.com.expensys.service.ExpPersonService;
import ec.com.expensys.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
@PreAuthorize("denyAll()")
public class ExpPersonController {

    private final ExpPersonService expPersonService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<PersonDto>> findAll(){
         return ResponseEntity.ok(expPersonService.findAll());
    }

    //TODO para pruebas de seguridad
    @GetMapping("/sec")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<List<PersonDto>> findAll2(){
        return ResponseEntity.ok(expPersonService.findAll());
    }
}
