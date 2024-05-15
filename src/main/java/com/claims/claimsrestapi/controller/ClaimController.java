package com.claims.claimsrestapi.controller;

import com.claims.claimsrestapi.dto.ClaimDto;
import com.claims.claimsrestapi.service.ClaimService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    private ClaimService claimService;

    // Build Add Claim ReST API
    @PostMapping
    public ResponseEntity<ClaimDto> createClaim(@RequestBody ClaimDto claimDto){
        ClaimDto savedClaim = claimService.createClaim(claimDto);
        return new ResponseEntity<>(savedClaim, HttpStatus.CREATED);
    }

    // Build Get Claim ReST API
    @GetMapping("{id}")
    public ResponseEntity<ClaimDto> getClaimById(@PathVariable("id") Long claimId){
        ClaimDto claimDto = claimService.getClaimById(claimId);
        return ResponseEntity.ok(claimDto);
    }

    // Build Get All Claims ReST API
    @GetMapping
    public ResponseEntity<List<ClaimDto>> getAllClaims(){
        List<ClaimDto> claims = claimService.getAllClaims();
        return ResponseEntity.ok(claims);
    }

    // Build Update Claim ReST API
    @PutMapping("{id}")
    public ResponseEntity<ClaimDto> updatedClaim(@PathVariable("id") Long claimId,
                                                 @RequestBody ClaimDto updatedClaim){
        ClaimDto claimDto = claimService.updateClaim(claimId, updatedClaim);
        return ResponseEntity.ok(claimDto);
    }
}
