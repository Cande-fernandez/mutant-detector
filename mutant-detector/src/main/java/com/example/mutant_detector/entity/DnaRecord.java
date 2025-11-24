//Entidad JPA que mapea a la tabla para almacenar la información del ADN
package com.example.mutant_detector.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "dna_records")
@Data
@NoArgsConstructor
public class DnaRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "dna_hash",unique = true,nullable = false,length = 64) //asegura que no haya registros duplicados de ADN
    private String dnaHash;

    @Column(name="is_mutant",nullable = false) //almacena el resultado del análisis, true:mutante, false: humano
    private boolean mutant;

    @Column(name ="created_at",nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public DnaRecord(String dnaHash, boolean mutant){
        this.dnaHash =dnaHash;
        this.mutant=mutant;
    }
}
