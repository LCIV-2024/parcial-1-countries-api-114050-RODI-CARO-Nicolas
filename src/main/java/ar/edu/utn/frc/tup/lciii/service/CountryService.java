package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.SaveCountriesRequestDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.response.CountryDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

        private final CountryRepository countryRepository;

        private final String[] availableContinents = { "Africa", "Americas", "Asia", "Europe", "Oceania", "Antarctic" };

        private final String[] availableLanguages = { "English", "Spanish", "French", "German", "Portuguese", "Chinese", "Arabic", "Russian", "Hindi", "Swahili" };


        private final RestTemplate restTemplate;

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }


        public List<CountryDTO> saveCountriesByAmount(SaveCountriesRequestDTO requestDTO) {

                List<Country> allCountries = getAllCountries();
                Collections.shuffle(allCountries);

                Long amountToSave = requestDTO.getAmountOfCountryToSave();

                List<Country> countriesToSave = allCountries.stream()
                        .limit(amountToSave)
                        .collect(Collectors.toList());

                List<CountryEntity> countryEntities = countriesToSave.stream()
                        .map(this::mapToEntity)
                        .collect(Collectors.toList());


                countryRepository.saveAll(countryEntities);

                return countryEntities.stream()
                        .map(this::mapToDTOFromCountryEntity)
                        .collect(Collectors.toList());

        }

        public List<CountryDTO> getCountriesByContinent(String continent)
        {
                boolean isValidContinent = Arrays.stream(availableContinents)
                        .anyMatch(validContinent -> validContinent.equals(continent));

                if(!isValidContinent || continent.isEmpty()){
                        throw new IllegalArgumentException("Continent requested not valid");
                }

                List<Country> allCountries = getAllCountries();
                List<CountryDTO> countryDTOList = new ArrayList<>();
                countryDTOList = allCountries.stream()
                        .filter(country -> country.getRegion().equals(continent))
                        .map(this::mapToDTO)
                        .collect(Collectors.toList());

                return countryDTOList;
        }



        public List<CountryDTO> getCountriesByLanguage(String language)
        {
                boolean isValidLanguage = Arrays.stream(availableLanguages)
                        .anyMatch(lang -> lang.equals(language));

                if(!isValidLanguage || language.isEmpty()){
                        throw new IllegalArgumentException("Language requested not valid");
                }

                List<Country> allCountries = getAllCountries();
                List<CountryDTO> countryDTOList = new ArrayList<>();
                System.out.println(allCountries.get(0));
                countryDTOList = allCountries.stream()
                        .filter(country -> country.getLanguages() != null && country.getLanguages().containsValue(language) )
                        .map(this::mapToDTO)
                        .collect(Collectors.toList());

                return countryDTOList;
        }



        public List<CountryDTO> getAllCountriesResponse(String name, String code) {

                List<Country> allCountries = getAllCountries();
                List<CountryDTO> countryDTOList = new ArrayList<>();

                if (name == null && code == null) {
                        countryDTOList = allCountries.stream()
                                .map(this::mapToDTO)
                                .collect(Collectors.toList());
                }
                else if (name != null && code == null) {
                        countryDTOList = allCountries.stream()
                                .filter(country -> country.getName().equalsIgnoreCase(name))
                                .map(this::mapToDTO)
                                .collect(Collectors.toList());
                }
                else if (name == null && code != null) {
                        countryDTOList = allCountries.stream()
                                .filter(country -> country.getCode().equalsIgnoreCase(code))
                                .map(this::mapToDTO)
                                .collect(Collectors.toList());
                }

                return countryDTOList;
        }



        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .code((String) countryData.get("cca3") )
                        .borders((List<String>) countryData.get("borders"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .region((String) countryData.get("region"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .build();
        }


        private CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }

        private CountryDTO mapToDTOFromCountryEntity(CountryEntity country) {
                return new CountryDTO(country.getCode(), country.getName());
        }

        private CountryEntity mapToEntity(Country country) {
                CountryEntity entity = new CountryEntity();
                entity.setName(country.getName());
                entity.setCode(country.getCode());
                entity.setPopulation(country.getPopulation());
                entity.setArea(country.getArea());
                return entity;
        }

}