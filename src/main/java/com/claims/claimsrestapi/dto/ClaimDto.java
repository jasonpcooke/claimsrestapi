package com.claims.claimsrestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimDto {
    private Long id;
    private BigDecimal amount;
    private String status;
    private Date createdDateTime;
    private Date updatedDateTime;

}
