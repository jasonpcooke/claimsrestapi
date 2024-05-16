package com.claims.claimsrestapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.claims.claimsrestapi.dto.ClaimDto;
import com.claims.claimsrestapi.service.ClaimService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ClaimControllerTest {

    @Mock
    private ClaimService claimService;

    @InjectMocks
    private ClaimController claimController;

    @Test
    void testCreateClaim() {
        // Given
        ClaimDto claimDto = new ClaimDto(); // create a sample ClaimDto
        ClaimDto savedClaimDto = new ClaimDto(); // create a sample saved ClaimDto
        when(claimService.createClaim(any(ClaimDto.class))).thenReturn(savedClaimDto); // mock claimService.createClaim to return the saved claim

        // When
        ResponseEntity<ClaimDto> responseEntity = claimController.createClaim(claimDto);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()); // verify the status code
        assertEquals(savedClaimDto, responseEntity.getBody()); // verify that the response body matches the saved claim
        verify(claimService, times(1)).createClaim(any(ClaimDto.class)); // verify that claimService.createClaim was called once with any ClaimDto object
    }

    @Test
    void testGetClaim() {
        // Given
        Long claimId = 1L;

        // When
        ResponseEntity<ClaimDto> responseEntity = claimController.getClaimById(claimId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(claimService).getClaimById(claimId);
    }

    @Test
    void testGetAllClaims() throws Exception {
        // Given
        List<ClaimDto> mockClaims = new ArrayList<>();
        ClaimDto claim1 = new ClaimDto();
        claim1.setId(1L);
        mockClaims.add(claim1);
        ClaimDto claim2 = new ClaimDto();
        claim2.setId(2L);
        mockClaims.add(claim2);

        // When
        when(claimService.getAllClaims()).thenReturn(mockClaims); // Mocking claimService.getAllClaims() to return mockClaims

        // Setting up MockMvc
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(claimController).build();

        // Then
        mockMvc.perform(get("/api/claims")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(mockClaims.size()))
                .andExpect(jsonPath("$[0].id").value(claim1.getId()))
                .andExpect(jsonPath("$[1].id").value(claim2.getId()));
        verify(claimService, times(1)).getAllClaims(); // Verifying that getAllClaims method of claimService is called exactly once
    }

    @Test
    void testUpdatedClaim() {
        // Given
        Long claimId = 1L;
        ClaimDto updatedClaimDto = new ClaimDto();
        updatedClaimDto.setAmount(BigDecimal.valueOf(100.00));
        updatedClaimDto.setStatus("APPROVED");

        ClaimDto mockClaimDto = new ClaimDto();
        mockClaimDto.setId(claimId);
        mockClaimDto.setAmount(BigDecimal.valueOf(200.00));
        mockClaimDto.setStatus("REJECTED");

        when(claimService.updateClaim(claimId, updatedClaimDto)).thenReturn(mockClaimDto);

        // When
        ResponseEntity<ClaimDto> responseEntity = claimController.updatedClaim(claimId, updatedClaimDto);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockClaimDto, responseEntity.getBody());
    }

    @Test
    void testDeleteClaim() {
        // Given
        Long claimId = 1L;

        // When
        ResponseEntity<String> responseEntity = claimController.deleteClaim(claimId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Claim deleted successfully.", responseEntity.getBody());
        verify(claimService, times(1)).deleteClaim(claimId);
    }
}
