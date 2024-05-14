package com.claims.claimsrestapi.service.impl;

import com.claims.claimsrestapi.dto.ClaimDto;
import com.claims.claimsrestapi.entity.Claim;
import com.claims.claimsrestapi.mapper.ClaimMapper;
import com.claims.claimsrestapi.repository.ClaimRepository;
import com.claims.claimsrestapi.service.ClaimService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClaimServiceImpl implements ClaimService {

    private ClaimRepository claimRepository;

    @Override
    public ClaimDto createClaim(ClaimDto claimDto) {

        Claim claim = ClaimMapper.mapToClaim(claimDto);
        Claim savedClaim = claimRepository.save(claim);
        return ClaimMapper.mapToClaimDto(savedClaim);
    }
}
