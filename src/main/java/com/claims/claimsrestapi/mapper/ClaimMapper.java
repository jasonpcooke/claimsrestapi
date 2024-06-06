package com.claims.claimsrestapi.mapper;

import com.claims.claimsrestapi.dto.ClaimDto;
import com.claims.claimsrestapi.entity.Claim;

public class ClaimMapper {

    public static ClaimDto mapToClaimDto(Claim claim){
        return new ClaimDto(
                claim.getId(),
                claim.getNotes(),
                claim.getAmount(),
                claim.getStatus(),
                claim.getCreatedDateTime(),
                claim.getUpdatedDateTime()
        );
    }

    public static Claim mapToClaim(ClaimDto claimDto){
        return new Claim(
                claimDto.getId(),
                claimDto.getNotes(),
                claimDto.getAmount(),
                claimDto.getStatus(),
                claimDto.getCreatedDateTime(),
                claimDto.getUpdatedDateTime()
        );
    }
}
