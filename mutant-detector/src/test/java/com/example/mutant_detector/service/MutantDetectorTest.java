package com.example.mutant_detector.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MutantDetectorTest {

    private final MutantDetector detector = new MutantDetector();

    // --- CASOS MUTANTES (DEBEN DAR TRUE) ---

    @Test
    void testHorizontalSequence() {
        String[] dna = {
                "AAAA",
                "TGCT",
                "CGTA",
                "GTAC"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testVerticalSequence() {
        String[] dna = {
                "ATGC",
                "ATGC",
                "ATGC",
                "ATGC"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testDiagonalSequence() {
        String[] dna = {
                "AAGT",
                "CAAG",
                "TCAG",
                "GTCA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testInverseDiagonalSequence() {
        String[] dna = {
                "GAAA",
                "AGAT",
                "ATGA",
                "TAAG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testTwoDifferentSequencesMutant() {
        // Horizontal + vertical
        String[] dna = {
                "AAAA",
                "AAGT",
                "TCGT",
                "TCGT"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantExampleOfChallenge() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    // --- CASOS HUMANOS (DEBEN DAR FALSE) ---

    @Test
    void testHumanWithOnlyOneSequence() {
        String[] dna = {
                "AAAA",
                "TGCT",
                "CTGA",
                "AGTC"
        };
        // Solo 1 secuencia horizontal → humano
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testHumanNoSequences() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "GTCA",
                "TGAC"
        };
        assertFalse(detector.isMutant(dna));
    }

    // --- VALIDACIONES NXN ---

    @Test
    void testInvalidMatrixNotSquare() {
        String[] dna = { "ATGC", "ATG" };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testInvalidEmptyArray() {
        String[] dna = {};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testInvalidNullArray() {
        assertFalse(detector.isMutant(null));
    }

    @Test
    void testInvalidRowNull() {
        String[] dna = { "ATGC", null, "GTCA", "TGAC" };
        assertFalse(detector.isMutant(dna));
    }

    // --- VALIDACIÓN DE CARACTERES ---

    @Test
    void testInvalidCharacters() {
        String[] dna = { "ABCD", "ATGC", "ATGC", "ATGC" };
        assertFalse(detector.isMutant(dna));
    }

    // --- CASOS DE BORDE ---

    @Test
    void testSequenceAtMatrixBorderHorizontal() {
        String[] dna = {
                "TTTT",
                "AGCT",
                "CGTA",
                "GTAC"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testSequenceAtMatrixBorderVertical() {
        String[] dna = {
                "AAGT",
                "AAGT",
                "AAGT",
                "AAGT"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testSmallestPossibleMatrix4x4Human() {
        String[] dna = {
                "ATGC",
                "TGCA",
                "CATG",
                "GTAC"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testSmallestPossibleMatrix4x4Mutant() {
        String[] dna = {
                "AAAA",
                "TGCA",
                "CATG",
                "GTAC"
        };
        assertTrue(detector.isMutant(dna));
    }
}

