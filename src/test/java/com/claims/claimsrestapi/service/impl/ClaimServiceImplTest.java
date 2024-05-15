package com.claims.claimsrestapi.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.claims.claimsrestapi.dto.ClaimDto;
import com.claims.claimsrestapi.entity.Claim;
import com.claims.claimsrestapi.exception.ResourceNotFoundException;
import com.claims.claimsrestapi.mapper.ClaimMapper;
import com.claims.claimsrestapi.repository.ClaimRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClaimServiceImplTest {

    @Mock
    private ClaimRepository claimRepository;

    @InjectMocks
    private ClaimServiceImpl claimService;

    @Test
    void testCreateClaim() {
        // Given
        ClaimDto claimDto = new ClaimDto(); // create a sample ClaimDto
        Claim claim = ClaimMapper.mapToClaim(claimDto); // map ClaimDto to Claim
        when(claimRepository.save(any(Claim.class))).thenReturn(claim); // mock claimRepository.save to return the claim

        // When
        ClaimDto result = claimService.createClaim(claimDto);

        // Then
        assertTrue(new ReflectionEquals(claimDto).matches(result)); // verify that the result matches the input claimDto
        verify(claimRepository, times(1)).save(any(Claim.class)); // verify that claimRepository.save was called once with any Claim object
    }

    @Test
    void testGetClaimById_ExistingClaim() {
        // Given
        Long claimId = 1L;
        Claim claim = new Claim();
        claim.setId(claimId); // set a dummy claim's Id to claimId
        when(claimRepository.findById(claimId)).thenReturn(Optional.of(claim)); // When findById is called using claimId, return an Optional containing the specified claim

        // When
        ClaimDto result = claimService.getClaimById(claimId);

        // Then
        assertNotNull(result); // Assert that a Dto has been mapped with a nonnull result
        assertEquals(claimId, result.getId()); // Assert that the id of the result matches the dummy claim's id
        verify(claimRepository, times(1)).findById(claimId); // Verify that findById was invoked once
    }

    @Test
    void testGetClaimById_NonExistingClaim() {
        // Given
        Long claimId = 1L;
        when(claimRepository.findById(claimId)).thenReturn(Optional.empty()); // Return an empty Optional as a dummy claim has not been instantiated

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> claimService.getClaimById(claimId)); // Assert that an exception has been thrown due to the claim service not finding a claim with the provided claimId
        verify(claimRepository, times(1)).findById(claimId); // Verify that findById was invoked once
    }

    @Test
    void testGetAllClaims() {
        // Given
        List<Claim> mockClaims = new ArrayList<>();
        Claim claim1 = new Claim();
        claim1.setId(1L);
        mockClaims.add(claim1);
        Claim claim2 = new Claim();
        claim2.setId(2L);
        mockClaims.add(claim2);

        when(claimRepository.findAll()).thenReturn(mockClaims); // Mocking claimRepository.findAll() to return mockClaims

        // When
        List<ClaimDto> result = claimService.getAllClaims(); // Calling the method under test

        // Then
        assertNotNull(result); // Asserting that the result is not null
        assertEquals(2, result.size()); // Asserting that the size of the result list is 2
        assertEquals(1L, result.get(0).getId()); // Asserting the first claim ID in the result list
        assertEquals(2L, result.get(1).getId()); // Asserting the second claim ID in the result list
        verify(claimRepository, times(1)).findAll(); // Verifying that findAll method of claimRepository is called exactly once
    }
}