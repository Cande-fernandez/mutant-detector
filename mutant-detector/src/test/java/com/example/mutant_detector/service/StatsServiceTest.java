package com.example.mutant_detector.service;

import com.example.mutant_detector.dto.StatsResponse;
import com.example.mutant_detector.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StatsServiceTest {

    private final DnaRecordRepository repository = mock(DnaRecordRepository.class);
    private final StatsService service = new StatsService(repository);

    @Test
    void testStats() {
        when(repository.countByMutant(true)).thenReturn(40L);
        when(repository.countByMutant(false)).thenReturn(100L);

        StatsResponse response = service.getStats();

        assertEquals(40, response.getCountMutantDna());
        assertEquals(100, response.getCountHumanDna());
        assertEquals(0.4, response.getRatio());
    }

    @Test
    void testZeroHumans() {
        when(repository.countByMutant(true)).thenReturn(10L);
        when(repository.countByMutant(false)).thenReturn(0L);

        StatsResponse response = service.getStats();

        assertEquals(0.0, response.getRatio());
    }
    @Test
    void testZeroMutants() {
        when(repository.countByMutant(true)).thenReturn(0L);
        when(repository.countByMutant(false)).thenReturn(50L);

        StatsResponse response = service.getStats();

        assertEquals(0, response.getCountMutantDna());
        assertEquals(50, response.getCountHumanDna());
        assertEquals(0.0, response.getRatio());
    }

    @Test
    void testBothCountsZero() {
        when(repository.countByMutant(true)).thenReturn(0L);
        when(repository.countByMutant(false)).thenReturn(0L);

        StatsResponse response = service.getStats();

        assertEquals(0.0, response.getRatio());
    }

    @Test
    void testLargeValues() {
        when(repository.countByMutant(true)).thenReturn(1_000_000L);
        when(repository.countByMutant(false)).thenReturn(2_000_000L);

        StatsResponse response = service.getStats();

        assertEquals(1_000_000, response.getCountMutantDna());
        assertEquals(2_000_000, response.getCountHumanDna());
        assertEquals(0.5, response.getRatio());
    }

    @Test
    void testRepositoryCalledTwice() {
        when(repository.countByMutant(anyBoolean())).thenReturn(10L);

        service.getStats();

        verify(repository, times(1)).countByMutant(true);
        verify(repository, times(1)).countByMutant(false);
    }

}