//Controller REST de Spring Boot con dos endpoints
package com.example.mutant_detector.controller;

import com.example.mutant_detector.dto.DnaRequest;
import com.example.mutant_detector.dto.StatsResponse;
import com.example.mutant_detector.service.MutantService;
import com.example.mutant_detector.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "Mutant Detector", description = "API para deteccion de mutantes")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    @PostMapping("/mutant")
    @Operation(summary = "Verificar si un ADN es mutante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Es mutante"),
            @ApiResponse(responseCode = "403", description = "No es mutante"),
            @ApiResponse(responseCode = "400", description = "ADN inválido")
    })
    public ResponseEntity<Void> isMutant (@Valid @RequestBody DnaRequest request){
        boolean isMutant = mutantService.isMutantAndSave(request.getDna());

        if (isMutant){
            return ResponseEntity.status(HttpStatus.OK).build ();
        } else {
            return ResponseEntity.status (HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("stats")
    @Operation(summary = "Obtener estadísticas de mutantes y humanos")
    public ResponseEntity<StatsResponse> getStats (){
        StatsResponse stats = statsService.getStats();
        return ResponseEntity.ok (stats);
    }
}
