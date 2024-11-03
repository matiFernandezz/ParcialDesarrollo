package com.example.parcial.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DnaServiceTests {
    @Test
    public void testFilas() {
        String[] dna = {
                "ATGCGA",
                "TTTTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(DnaService.isMutant(dna));
    }

    @Test
    public void testColumnas() {
        String[] dna = {
                "ATGCGA",
                "AAGTGC",
                "ATTAGT",
                "AGAAGG",
                "TCCCTA",
                "TCACTG"
        };
        assertTrue(DnaService.isMutant(dna));
    }

    @Test
    public void testMainDiagonales() {
        String[] dna = {
                "ATGCGA",
                "CAGTAC",
                "TCATGT",
                "AGCAAG",
                "CCTCTA",
                "TCACTG"
        };
        assertTrue(DnaService.isMutant(dna));
    }

    @Test
    public void testMainDiagonalesIzquierda() {
        String[] dna = {
                "ATGCGA",
                "CAGGAC",
                "TTGAGT",
                "AGAATG",
                "CCCGTA",
                "TCTCTG"
        };
        assertTrue(DnaService.isMutant(dna));
    }

    @Test
    public void testFilasyColumnas() {
        String[] dna = {
                "CTGCGA",
                "CAGTGC",
                "CTGTCT",
                "CGATGG",
                "AAAATA",
                "TCACTG"
        };
        assertTrue(DnaService.isMutant(dna));
    }
    @Test
    public void testFilasyDiagonales() {
        String[] dna = {
                "CTGCGA",
                "CCGTGC",
                "TTCTCT",
                "GGACGG",
                "AAAATA",
                "TCACTG"
        };
        assertTrue(DnaService.isMutant(dna));
    }
    @Test
    public void testColumnasYDiagonales() {
        String[] dna = {
                "CTGCGA",
                "CATTGC",
                "CTGTCT",
                "CGATTG",
                "AAAGTA",
                "TCACTG"
        };
        assertTrue(DnaService.isMutant(dna));
    }
    @Test
    public void testHuman() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(DnaService.isMutant(dna));
    }


}
