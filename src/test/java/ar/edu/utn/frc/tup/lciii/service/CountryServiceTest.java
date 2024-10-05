package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.response.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class CountryServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @SpyBean
    private CountryService countryService;

    @Test
    void testGetAllCountriesResponse() {
        List<Map<String, Object>> mockResponse = Arrays.asList(
                Map.of(
                        "name", Map.of("common", "Argentina"),
                        "cca3", "ARG",
                        "borders", List.of("BRA", "CHL"),
                        "population", 2323,
                        "area", 2323232,
                        "region", "Americas"

                ),
                Map.of(
                        "name", Map.of("common", "Brasil"),
                        "cca3", "BRA",
                        "borders", List.of("ARG", "PAR"),
                        "population", 23232,
                        "area", 232323,
                        "region", "Americas"

                )
        );

        when(restTemplate.getForObject(anyString(), ArgumentMatchers.eq(List.class)))
                .thenReturn(mockResponse);


        List<CountryDTO> result = countryService.getAllCountriesResponse("Argentina", null);

        assertEquals(1, result.size());
        assertEquals("Argentina", result.get(0).getName());
        assertEquals("ARG", result.get(0).getCode());
    }

    @Test
    void getCountriesByContinent_invalidContinent() {
        String invalidContinent = "Pangea";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            countryService.getCountriesByContinent(invalidContinent);
        });

        assertEquals("Continent requested not valid", exception.getMessage());
    }

    @Test
    void getCountriesByContinent_validContinent() {
        String validContinent = "Asia";

        List<Country> mockCountries = Arrays.asList(
                Country.builder().name("Japan").code("JP").region("Asia").build(),
                Country.builder().name("India").code("IN").region("Asia").build(),
                Country.builder().name("Germany").code("DE").region("Europe").build()
        );

        Mockito.doReturn(mockCountries).when(countryService).getAllCountries();

        List<CountryDTO> result = countryService.getCountriesByContinent(validContinent);

        assertEquals(2, result.size());
        assertEquals("JP", result.get(0).getCode());
        assertEquals("Japan", result.get(0).getName());
        assertEquals("IN", result.get(1).getCode());
        assertEquals("India", result.get(1).getName());
    }

    @Test
    void getCountriesByLanguage_validLanguage() {
        String validLanguage = "English";

        HashMap<String, String > englishHashMap = new HashMap<>();
        englishHashMap.put("1", "English");

        List<Country> mockCountries = Arrays.asList(
                Country.builder().name("United States").code("USA").region("Americas").languages(englishHashMap).build(),
                Country.builder().name("India").code("IN").region("Asia").languages(englishHashMap).build(),
                Country.builder().name("United Kingdom").code("UK").region("Europe").languages(englishHashMap).build()
        );

        Mockito.doReturn(mockCountries).when(countryService).getAllCountries();

        List<CountryDTO> result = countryService.getCountriesByLanguage(validLanguage);

        assertEquals(3, result.size());
        assertEquals("USA", result.get(0).getCode());
        assertEquals("IN", result.get(1).getCode());
        assertEquals("India", result.get(1).getName());
    }





}