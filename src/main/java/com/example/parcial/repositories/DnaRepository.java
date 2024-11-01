package com.example.parcial.repositories;

import com.example.parcial.entities.Dna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DnaRepository  extends JpaRepository<Dna, Long> {
    Optional<Dna> findByDna(String dnaSequence); //Busco por ADN

    long countByIsMutant(boolean isMutant); //Cuento los mutantes
}
