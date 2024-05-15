package com.claims.claimsrestapi.service;

import com.claims.claimsrestapi.dto.ClaimDto;

public interface ClaimService {
    ClaimDto createClaim(ClaimDto claimDto);

    ClaimDto getClaimById(Long claimId);
}
