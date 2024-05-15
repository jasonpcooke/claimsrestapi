package com.claims.claimsrestapi.mapper;

import com.claims.claimsrestapi.dto.ClaimDto;
import com.claims.claimsrestapi.entity.Claim;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClaimMapperTest {

    @Test
    void testMapToClaimDto() throws ParseException {
        // Initialise Dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdDateTime = dateFormat.parse("2020-3-15 9:00:00");
        Date updatedDateTime = dateFormat.parse("2020-3-16 10:00:00");

        // Create a sample Claim object
        Claim claim = new Claim();
        claim.setAmount(BigDecimal.valueOf(100.0));
        claim.setStatus("CREATED");
        claim.setCreatedDateTime(createdDateTime);
        claim.setUpdatedDateTime(updatedDateTime);

        // Map Claim object to ClaimDto
        ClaimDto claimDto = ClaimMapper.mapToClaimDto(claim);

        // Verify that ClaimDto is mapped correctly
        assertEquals(claim.getId(), claimDto.getId());
        assertEquals(claim.getAmount(), claimDto.getAmount()); // Double comparison with delta
        assertEquals(claim.getStatus(), claimDto.getStatus());
        assertEquals(claim.getCreatedDateTime(), claimDto.getCreatedDateTime());
        assertEquals(claim.getUpdatedDateTime(), claimDto.getUpdatedDateTime());
    }

    @Test
    void testMapToClaim() throws ParseException {
        // Initialise Dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdDateTime = dateFormat.parse("2020-3-15 9:00:00");
        Date updatedDateTime = dateFormat.parse("2020-3-16 10:00:00");

        // Create a sample Claim object
        ClaimDto claimDto = new ClaimDto();
        claimDto.setAmount(BigDecimal.valueOf(100.0));
        claimDto.setStatus("CREATED");
        claimDto.setCreatedDateTime(createdDateTime);
        claimDto.setUpdatedDateTime(updatedDateTime);

        // Map ClaimDto object to Claim
        Claim claim = ClaimMapper.mapToClaim(claimDto);

        // Verify that Claim is mapped correctly
        assertEquals(claimDto.getId(), claim.getId());
        assertEquals(claimDto.getAmount(), claim.getAmount()); // Double comparison with delta
        assertEquals(claimDto.getStatus(), claim.getStatus());
        assertEquals(claimDto.getCreatedDateTime(), claim.getCreatedDateTime());
        assertEquals(claimDto.getUpdatedDateTime(), claim.getUpdatedDateTime());
    }
}