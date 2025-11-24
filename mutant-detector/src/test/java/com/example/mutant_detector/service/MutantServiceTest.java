package com.example.mutant_detector.service;

import com.example.mutant_detector.entity.DnaRecord;
import com.example.mutant_detector.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MutantServiceTest {

    private final MutantDetector detector = Mockito.mock(MutantDetector.class);
    private final DnaRecordRepository repository = Mockito.mock(DnaRecordRepository.class);

    private final MutantService service = new MutantService(detector, repository);

    @Test
    void testAlreadyExistsInDatabase() {
        String[] dna = {"AAAA", "AAAA", "AAAA", "AAAA"};
        String hash = "123abc";

        // Simula ADN existente
        when(repository.findByDnaHash(anyString()))
                .thenReturn(Optional.of(new DnaRecord(hash, true)));

        boolean result = service.isMutantAndSave(dna);

        assertTrue(result);
        verify(detector, never()).isMutant(any());
        verify(repository, never()).save(any());
    }

    @Test
    void testNewDnaIsSaved() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAAG"};

        when(repository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());

        when(detector.isMutant(dna)).thenReturn(true);

        boolean result = service.isMutantAndSave(dna);

        assertTrue(result);
        verify(repository, times(1)).save(any(DnaRecord.class));
    }
    @Test
    void testNewDnaIsHumanAndSaved() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAC"};

        when(repository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());
        when(detector.isMutant(dna)).thenReturn(false);

        boolean result = service.isMutantAndSave(dna);

        assertFalse(result);
        verify(repository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void testDetectorThrowsException() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAC"};

        when(repository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());
        when(detector.isMutant(dna)).thenThrow(new RuntimeException("fail"));

        assertThrows(RuntimeException.class, () -> service.isMutantAndSave(dna));
    }

    @Test
    void testRepositoryThrowsOnSave() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAC"};

        when(repository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());
        when(detector.isMutant(dna)).thenReturn(true);
        when(repository.save(any())).thenThrow(new RuntimeException("db error"));

        assertThrows(RuntimeException.class, () -> service.isMutantAndSave(dna));
    }

    @Test
    void testFindByDnaHashCalledOnce() {
        String[] dna = {"AAAA", "AAAA", "AAAA", "AAAA"};

        when(repository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());
        when(detector.isMutant(dna)).thenReturn(true);

        service.isMutantAndSave(dna);

        verify(repository, times(1)).findByDnaHash(anyString());
    }

    @Test
    void testSavedRecordHasCorrectMutantValue() {
        String[] dna = {"AAAA", "AAAA", "AAAA", "AAAA"};

        when(repository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());
        when(detector.isMutant(dna)).thenReturn(true);

        service.isMutantAndSave(dna);

        verify(repository).save(argThat(record -> record.isMutant()));
    }

    @Test
    void testSavedRecordHasCorrectHash() {
        String[] dna = {"AAAA", "AAAA", "AAAA", "AAAA"};

        when(repository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());
        when(detector.isMutant(any())).thenReturn(true);

        service.isMutantAndSave(dna);

        verify(repository).save(argThat(record -> record.getDnaHash() != null && !record.getDnaHash().isEmpty()));
    }

}