//Lógica de negocio para la detección de mutantes y el guardado en la BD. Calcula el hash del ADN
package com.example.mutant_detector.service;

import com.example.mutant_detector.entity.DnaRecord;
import com.example.mutant_detector.exception.DnaHashCalculationException;
import com.example.mutant_detector.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRecordRepository repository;

    public boolean isMutantAndSave(String[] dna) {
        String hash = generateHash(dna);

        return repository.findByDnaHash(hash)
                .map(DnaRecord::isMutant)
                .orElseGet(() -> {
                    boolean isMutant = mutantDetector.isMutant(dna);
                    repository.save(new DnaRecord(hash, isMutant));
                    return isMutant;
                });
    }

    private String generateHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String joined = String.join("", dna);
            byte[] hashBytes = digest.digest(joined.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new DnaHashCalculationException("Error generando hash de ADN", e);
        }
    }
}