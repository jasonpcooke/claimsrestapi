package com.claims.claimsrestapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import com.claims.claimsrestapi.dto.ClaimDto;
import com.claims.claimsrestapi.service.ClaimService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
}
