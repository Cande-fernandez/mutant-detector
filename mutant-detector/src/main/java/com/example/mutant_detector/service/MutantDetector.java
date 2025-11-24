package com.example.mutant_detector.service;

import org.springframework.stereotype.Service;

@Service
public class MutantDetector {
    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(String[] dna) {
        if (!isValidDna(dna)) {
            return false;
        }

        final int n = dna.length;
        // Conversión a char[][] (Optimización #1)
        char[][] matrix = new char[n][];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }
        int sequencesFound = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // Horizontal →
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                }

                // Vertical ↓
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col)) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                }

                // Diagonal ↘
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonal(matrix, row, col)) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                }

                // Diagonal inversa ↗
                if (row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
                    if (checkInverseDiagonal(matrix, row, col)) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean isValidDna(String[] dna) {
        if (dna == null || dna.length < SEQUENCE_LENGTH) {
            return false;
        }

        final int n = dna.length;

        for (String row : dna) {
            if (row == null || row.length() != n) {return false;} //no es NxN
            if (!row.matches("[ATCG]+")) return false;
        }

        return true;
    }


    //chequeo de manera horizontal
    private boolean checkHorizontal(char[][] m, int r, int c) {
        char base = m[r][c];
        return  m[r][c+1] == base &&
                m[r][c+2] == base &&
                m[r][c+3] == base;
    }
    //chequeo de manera vertical
    private boolean checkVertical(char[][] m, int r, int c) {
        char base = m[r][c];
        return  m[r+1][c] == base &&
                m[r+2][c] == base &&
                m[r+3][c] == base;
    }
    //chequeo de manera diagonal
    private boolean checkDiagonal(char[][] m, int r, int c) {
        char base = m[r][c];
        return  m[r+1][c+1] == base &&
                m[r+2][c+2] == base &&
                m[r+3][c+3] == base;
    }
    //chequeo diagonal inversa
    private boolean checkInverseDiagonal(char[][] m, int r, int c) {
        char base = m[r][c];
        return  m[r-1][c+1] == base &&
                m[r-2][c+2] == base &&
                m[r-3][c+3] == base;
    }
}
