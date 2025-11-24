//dto de salida
package com.example.mutant_detector.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Estadisticas de verificaciones de ADN")
public class StatsResponse {

    @Schema(description = "Cantidad de ADN mutante verificado")
    @JsonProperty("count_mutant_dna")
    private long countMutantDna;

    @Schema(description = "Cantidad de ADN humano verificado")
    @JsonProperty("count_human_dna")
    private long countHumanDna;

    @Schema(description = "Ratio mutante/humano")
    private double ratio;
}
