//Responsable de obtener las estadísticas desde la BD
package com.example.mutant_detector.service;

import com.example.mutant_detector.dto.StatsResponse;
import com.example.mutant_detector.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final DnaRecordRepository repository;

    public StatsResponse getStats (){
        long mutants = repository.countByMutant(true);
        long humans = repository.countByMutant(false);
        double ratio = humans == 0 ? 0.0 : (double) mutants / humans;

        return new StatsResponse(mutants, humans, ratio);
    }
}
