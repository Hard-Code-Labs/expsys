package ec.com.expensys.web.controller;

import ec.com.expensys.service.ExpPersonService;
import ec.com.expensys.web.record.PersonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/person")
public class ExpPersonController {

    private final ExpPersonService expPersonService;

    public ExpPersonController(ExpPersonService expPersonService) {
        this.expPersonService = expPersonService;
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> findAll(){
         return ResponseEntity.ok(expPersonService.findAll());
    }
}
