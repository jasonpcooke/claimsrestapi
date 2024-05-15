package com.claims.claimsrestapi.service;

import com.claims.claimsrestapi.dto.ClaimDto;

import java.util.List;

public interface ClaimService {
    ClaimDto createClaim(ClaimDto claimDto);

    ClaimDto getClaimById(Long claimId);

    List<ClaimDto> getAllClaims();
}
