package ar.edu.utn.frc.tup.lciii.dtos.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveCountriesRequestDTO {

    private Long amountOfCountryToSave;

}
