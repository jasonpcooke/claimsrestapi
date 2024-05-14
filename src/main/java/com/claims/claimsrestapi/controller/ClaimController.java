package com.claims.claimsrestapi.controller;

import com.claims.claimsrestapi.dto.ClaimDto;
import com.claims.claimsrestapi.service.ClaimService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    private ClaimService claimService;

    //Build Add Claim ReST API
    @PostMapping
    public ResponseEntity<ClaimDto> createClaim(@RequestBody ClaimDto claimDto){
        ClaimDto savedClaim = claimService.createClaim(claimDto);
        return new ResponseEntity<>(savedClaim, HttpStatus.CREATED);
    }

}
