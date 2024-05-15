package com.claims.claimsrestapi.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.claims.claimsrestapi.dto.ClaimDto;
import com.claims.claimsrestapi.entity.Claim;
import com.claims.claimsrestapi.exception.CreatedAndUpdatedDateTimeException;
import com.claims.claimsrestapi.exception.ResourceNotFoundException;
import com.claims.claimsrestapi.mapper.ClaimMapper;
import com.claims.claimsrestapi.repository.ClaimRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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

        // When
        when(claimRepository.findById(claimId)).thenReturn(Optional.empty()); // Return an empty Optional as a dummy claim has not been instantiated

        // Then
        assertThrows(
                ResourceNotFoundException.class,
                () -> claimService.getClaimById(claimId)); // Assert that an exception has been thrown due to the claim service not finding a claim with the provided claimId
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

    @Test
    void testUpdateClaim() {
        // Given
        Long claimId = 1L;

        LocalDateTime localDateTime = LocalDateTime.now();
        Date createdDateTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).minusDays(1).toInstant());
        Date updatedDateTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        ClaimDto updatedClaim = new ClaimDto();
        updatedClaim.setAmount(BigDecimal.valueOf(100.00));
        updatedClaim.setStatus("APPROVED");
        updatedClaim.setCreatedDateTime(createdDateTime);
        updatedClaim.setUpdatedDateTime(updatedDateTime);

        Claim claim = new Claim();
        claim.setId(claimId);
        claim.setAmount(BigDecimal.valueOf(50.00));
        claim.setStatus("REJECTED");
        claim.setCreatedDateTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).minusDays(3).toInstant()));
        claim.setUpdatedDateTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).minusDays(2).toInstant()));

        when(claimRepository.findById(claimId)).thenReturn(Optional.of(claim));
        when(claimRepository.save(any())).thenReturn(claim);

        // When
        ClaimDto result = claimService.updateClaim(claimId, updatedClaim);

        // Then
        assertEquals(claimId, result.getId());
        assertEquals(updatedClaim.getAmount(), result.getAmount());
        assertEquals(updatedClaim.getStatus(), result.getStatus());
        assertEquals(updatedClaim.getCreatedDateTime(), result.getCreatedDateTime());
        assertEquals(updatedClaim.getUpdatedDateTime(), result.getUpdatedDateTime());

        ArgumentCaptor<Claim> captor = ArgumentCaptor.forClass(Claim.class);
        verify(claimRepository).save(captor.capture());
        assertEquals(claimId, captor.getValue().getId());
        assertEquals(updatedClaim.getAmount(), captor.getValue().getAmount());
        assertEquals(updatedClaim.getStatus(), captor.getValue().getStatus());
        assertEquals(updatedClaim.getCreatedDateTime(), captor.getValue().getCreatedDateTime());
        assertEquals(updatedClaim.getUpdatedDateTime(), captor.getValue().getUpdatedDateTime());
    }

    @Test
    void testUpdateClaim_ResourceNotFoundException() {
        // Given
        Long claimId = 1L;

        LocalDateTime localDateTime = LocalDateTime.now();
        Date createdDateTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date updatedDateTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).minusDays(4).toInstant());

        ClaimDto updatedClaim = new ClaimDto();
        updatedClaim.setAmount(BigDecimal.valueOf(100.00));
        updatedClaim.setStatus("APPROVED");
        updatedClaim.setCreatedDateTime(createdDateTime);
        updatedClaim.setUpdatedDateTime(updatedDateTime);

        Claim claim = new Claim();
        claim.setId(claimId);
        claim.setAmount(BigDecimal.valueOf(50.00));
        claim.setStatus("REJECTED");
        claim.setCreatedDateTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).minusDays(3).toInstant()));
        claim.setUpdatedDateTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).minusDays(2).toInstant()));

        //  When
        when(claimRepository.findById(claimId)).thenReturn(Optional.of(claim));


        // Then
        assertThrows(
                CreatedAndUpdatedDateTimeException.class,
                () -> claimService.updateClaim(claimId, updatedClaim)
        );

        verify(claimRepository, never()).save(any());
    }

    @Test
    void testUpdateClaim_CreatedUpdatedDateTimeException() {
        // Given
        Long claimId = 1L;

        LocalDateTime localDateTime = LocalDateTime.now();
        Date createdDateTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date updatedDateTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).minusDays(4).toInstant());

        ClaimDto updatedClaim = new ClaimDto();
        updatedClaim.setAmount(BigDecimal.valueOf(100.00));
        updatedClaim.setStatus("APPROVED");
        updatedClaim.setCreatedDateTime(createdDateTime);
        updatedClaim.setUpdatedDateTime(updatedDateTime);

        //When
        when(claimRepository.findById(claimId)).thenReturn(Optional.empty());


        // Then
        assertThrows(
                ResourceNotFoundException.class,
                () -> claimService.updateClaim(claimId, updatedClaim)); // Assert that an exception has been thrown due to the claim service not finding a claim with the provided claimId
        verify(claimRepository, times(1)).findById(claimId); // Verify that findById was invoked once
        verify(claimRepository, never()).save(any());
    }

    @Test
    void testDeleteClaim_Success() {
        // Given
        Long claimId = 1L;
        when(claimRepository.findById(claimId)).thenReturn(java.util.Optional.of(new Claim()));

        // When
        claimService.deleteClaim(claimId);

        // Then
        verify(claimRepository, times(1)).deleteById(claimId);
    }

    @Test
    void testDeleteClaim_ClaimNotFound() {
        // Given
        Long claimId = 1L;
        when(claimRepository.findById(claimId)).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> claimService.deleteClaim(claimId));
        verify(claimRepository, never()).deleteById(claimId);
    }
}