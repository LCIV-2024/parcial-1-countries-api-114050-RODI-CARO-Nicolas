package ar.edu.utn.frc.tup.lciii.repository;

import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Completar segun corresponda
 */
@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
}
