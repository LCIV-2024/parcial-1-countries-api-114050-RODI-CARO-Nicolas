package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.response.CountryDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryDTO>> getCountries(@RequestParam(value="name") Optional<String> name, @RequestParam(value="code") Optional<String> code ) {
        return ResponseEntity.ok(countryService.getAllCountriesResponse(name.orElse(null), code.orElse(null) ));
    }

}