package ec.com.expensys.web.controller;

import ec.com.expensys.service.ExpPersonService;
import ec.com.expensys.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class ExpPersonController {

    private final ExpPersonService expPersonService;

    @GetMapping
    public ResponseEntity<List<PersonDto>> findAll(){
         return ResponseEntity.ok(expPersonService.findAll());
    }
}
