package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.SaveCountriesRequestDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.response.CountryDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryDTO>> getCountries(@RequestParam(value="name") Optional<String> name, @RequestParam(value="code") Optional<String> code ) {
        return ResponseEntity.ok(countryService.getAllCountriesResponse(name.orElse(null), code.orElse(null) ));
    }

    @PostMapping
    public ResponseEntity<List<CountryDTO>> saveCountries(@RequestBody SaveCountriesRequestDTO requestDTO) {
        return ResponseEntity.ok(countryService.saveCountriesByAmount(requestDTO));
    }

    @GetMapping("{continent}/continent")
    public ResponseEntity<List<CountryDTO>> getCountriesByContinent(@PathVariable String continent ) {
        return ResponseEntity.ok(countryService.getCountriesByContinent(continent));
    }

    @GetMapping("{language}/language")
    public ResponseEntity<List<CountryDTO>> getCountriesByLanguage(@PathVariable String language ) {
        return ResponseEntity.ok(countryService.getCountriesByLanguage(language));
    }

    @GetMapping("most-borders")
    public ResponseEntity<List<CountryDTO>> getCountriesWithMostBorders( ) {
        return ResponseEntity.ok(countryService.getCountriesWithMostBorders());
    }






}