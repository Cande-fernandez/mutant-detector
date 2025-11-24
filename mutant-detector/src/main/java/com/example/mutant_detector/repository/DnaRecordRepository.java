//Intefaz que extiende JpaRepository para facilitar el acceso a la BD para las operaciones relacionadas con la entidad DnaRecord
package com.example.mutant_detector.repository;

import com.example.mutant_detector.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DnaRecordRepository extends JpaRepository<DnaRecord, Long> {

    Optional<DnaRecord> findByDnaHash (String dnaHash); //Busca si un ADN ya fue analizado.
    long countByMutant (boolean mutant); //Cuenta cuantos son mutantes (true) y cuantos humanos (false)
}
