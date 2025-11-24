package com.example.mutant_detector.controller;

import com.example.mutant_detector.dto.DnaRequest;
import com.example.mutant_detector.dto.StatsResponse;
import com.example.mutant_detector.service.MutantService;
import com.example.mutant_detector.service.StatsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MutantController.class)
public class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testMutantEndpointReturns200() throws Exception {
        String[] dna = {"AAAA", "AAAA", "AAAA", "AAAA"};
        when(mutantService.isMutantAndSave(dna)).thenReturn(true);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new DnaRequest(dna))))
                .andExpect(status().isOk());
    }

    @Test
    void testMutantEndpointReturns403() throws Exception {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAC"};
        when(mutantService.isMutantAndSave(dna)).thenReturn(false);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new DnaRequest(dna))))
                .andExpect(status().isForbidden());
    }

    @Test
    void testMutantEndpointReturns400ForInvalidJson() throws Exception {
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid-json}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testMutantEndpointReturns400ForEmptyRequest() throws Exception {
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testMutantEndpointReturns400ForNullDna() throws Exception {
        DnaRequest request = new DnaRequest(null);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testMutantEndpointReturns400ForNonNxN() throws Exception {
        String[] dna = {"ATG", "CAGT", "TTAT"};
        DnaRequest request = new DnaRequest(dna);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testMutantEndpointReturns400ForInvalidCharacters() throws Exception {
        String[] dna = {"ATGX", "CAGT", "TTAT", "AGAC"};
        DnaRequest request = new DnaRequest(dna);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testMutantEndpointHandlesServiceExceptions() throws Exception {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAC"};
        when(mutantService.isMutantAndSave(dna)).thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new DnaRequest(dna))))
                .andExpect(status().isInternalServerError());
    }

    // -----------------------------
    // GET /stats tests
    // -----------------------------

    @Test
    void testStatsEndpointReturns200() throws Exception {
        when(statsService.getStats()).thenReturn(
                new com.example.mutant_detector.dto.StatsResponse(40, 100, 0.4)
        );

        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk());
    }

    @Test
    void testStatsEndpointReturnsCorrectJson() throws Exception {
        when(statsService.getStats()).thenReturn(
                new com.example.mutant_detector.dto.StatsResponse(10, 20, 0.5)
        );

        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(10))
                .andExpect(jsonPath("$.count_human_dna").value(20))
                .andExpect(jsonPath("$.ratio").value(0.5));
    }

    @Test
    void testStatsEndpointZeroHumans() throws Exception {
        when(statsService.getStats()).thenReturn(
                new com.example.mutant_detector.dto.StatsResponse(5, 0, 0)
        );

        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ratio").value(0.0));
    }

    @Test
    void testStatsEndpointLargeNumbers() throws Exception {
        when(statsService.getStats()).thenReturn(
                new com.example.mutant_detector.dto.StatsResponse(1000, 5000, 0.2)
        );

        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(1000))
                .andExpect(jsonPath("$.count_human_dna").value(5000))
                .andExpect(jsonPath("$.ratio").value(0.2));
    }

    @Test
    void testInvalidDnaReturns400() throws Exception {
        String[] dna = {"ATGX", "CAGT"}; // contiene caracter inválido X

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new DnaRequest(dna))))
                .andExpect(status().isBadRequest());
    }


}