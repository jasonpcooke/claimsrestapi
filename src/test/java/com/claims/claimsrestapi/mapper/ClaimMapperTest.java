package com.claims.claimsrestapi.mapper;

import com.claims.claimsrestapi.dto.ClaimDto;
import com.claims.claimsrestapi.entity.Claim;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClaimMapperTest {

    @Test
    void testMapToClaimDto() {
        // Create a sample Claim object
        Claim claim = new Claim();
        claim.setAmount(BigDecimal.valueOf(100.0));
        claim.setStatus("CREATED");

        // Map Claim object to ClaimDto
        ClaimDto claimDto = ClaimMapper.mapToClaimDto(claim);

        // Verify that ClaimDto is mapped correctly
        assertEquals(claim.getId(), claimDto.getId());
        assertEquals(claim.getAmount(), claimDto.getAmount()); // Double comparison with delta
        assertEquals(claim.getStatus(), claimDto.getStatus());
    }

    @Test
    void testMapToClaim()  {
        // Create a sample Claim object
        ClaimDto claimDto = new ClaimDto();
        claimDto.setAmount(BigDecimal.valueOf(100.0));
        claimDto.setStatus("CREATED");

        // Map ClaimDto object to Claim
        Claim claim = ClaimMapper.mapToClaim(claimDto);

        // Verify that Claim is mapped correctly
        assertEquals(claimDto.getId(), claim.getId());
        assertEquals(claimDto.getAmount(), claim.getAmount()); // Double comparison with delta
        assertEquals(claimDto.getStatus(), claim.getStatus());
    }
}