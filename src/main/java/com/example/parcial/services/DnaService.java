package com.example.parcial.services;

import com.example.parcial.entities.Dna;
import com.example.parcial.repositories.DnaRepository;
import com.example.parcial.validators.DnaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class DnaService {


    private final DnaRepository dnaRepository;
    private static final DnaValidator dnaValidator = new DnaValidator(); // Instancia del validador de ADN

    private static final int SEQUENCE_LENGTH = 4;
    private static final int MIN_MUTANT_SEQUENCE_COUNT = 2;

    @Autowired
    public DnaService(DnaRepository dnaRepository) {
        this.dnaRepository = dnaRepository;
    }

    public static boolean isMutant(String[] dna) {
        // Validación del ADN
        if (!dnaValidator.isValid(dna, null)) {
            throw new IllegalArgumentException("ADN no válido. Debe contener solo 'A', 'T', 'C', 'G' y ser una matriz cuadrada.");
        }

        int n = dna.length;
        Set<String> foundSequences = new HashSet<>();

        // Direcciones: Horizontal, Vertical, Diagonal derecha, Diagonal izquierda
        int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};

        // Procesamiento diagonal y por fila/columna para optimización
        for (int i = 0; i < n; i++) {
            for (int[] dir : directions) {
                // Búsqueda en filas, columnas y diagonales
                if (checkAndStoreSequence(dna, i, 0, dir[0], dir[1], foundSequences) ||
                        checkAndStoreSequence(dna, 0, i, dir[0], dir[1], foundSequences)) {
                    return true;
                }
            }
        }

        return false;
    }

    // Comprueba y almacena la secuencia si es válida y permite un retorno temprano
    private static boolean checkAndStoreSequence(String[] dna, int x, int y, int dx, int dy, Set<String> foundSequences) {
        int n = dna.length;
        int count = 1;

        while (x + dx * (count - 1) < n && y + dy * (count - 1) < n && x + dx * (count - 1) >= 0 && y + dy * (count - 1) >= 0) {
            char currentChar = dna[x].charAt(y);
            count = 1;

            for (int k = 1; k < SEQUENCE_LENGTH; k++) {
                int newX = x + k * dx;
                int newY = y + k * dy;

                if (newX < 0 || newY < 0 || newX >= n || newY >= n || dna[newX].charAt(newY) != currentChar) {
                    count = 1;
                    break;
                }
                count++;
            }

            if (count == SEQUENCE_LENGTH) {
                foundSequences.add(String.valueOf(currentChar).repeat(SEQUENCE_LENGTH));
                if (foundSequences.size() >= MIN_MUTANT_SEQUENCE_COUNT) {
                    return true;
                }
            }
            x += dx;
            y += dy;
        }

        return false;
    }

    public boolean analyzeDna(String[] dna) {
        String dnaSequence = String.join(",", dna);

        // Verificar si el ADN ya fue analizado
        Optional<Dna> existingDna = dnaRepository.findByDna(dnaSequence);
        if (existingDna.isPresent()) {
            return existingDna.get().isMutant();
        }

        // Determina si el ADN es mutante y guarda en la base de datos
        boolean isMutant = isMutant(dna);
        Dna dnaEntity = Dna.builder()
                .dna(dnaSequence)
                .isMutant(isMutant)
                .build();
        dnaRepository.save(dnaEntity);

        return isMutant;
    }
}


//Este algoritmo lo pense de manera tal que me permita evitar bucles anidados, de esta forma pense en un algoritmo que recorra
//directamente la matriz de una vez y en las cuatro direcciones posibles, asi se reduce el numero de verificaciones
//Ademas, como el ejercicio nos dice que si se encuentran al menos dos secuencias de 4 letras iguales consecutivas y que sean secuencias unicas basta para verificar que es mutante,
//implemente el pronto retorno de manera tal que si encuentro dos secuencias distintas, el algoritmo termina inmediatamente