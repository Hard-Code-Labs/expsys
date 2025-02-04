package ec.com.expensys.web.controller;

import ec.com.expensys.dto.CountryDto;
import ec.com.expensys.service.ExpCountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
@RequestMapping("/countries")
public class ExpCountryController {

    private final ExpCountryService expCountryService;

    @GetMapping
    public ResponseEntity<List<CountryDto>> findAll(){
        return ResponseEntity.ok(expCountryService.findAllActive());
    }

}
