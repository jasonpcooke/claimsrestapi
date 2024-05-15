package com.claims.claimsrestapi.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import com.claims.claimsrestapi.dto.ClaimDto;
import com.claims.claimsrestapi.entity.Claim;
import com.claims.claimsrestapi.mapper.ClaimMapper;
import com.claims.claimsrestapi.repository.ClaimRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;

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
}