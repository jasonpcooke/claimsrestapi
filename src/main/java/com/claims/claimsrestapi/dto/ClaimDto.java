package com.claims.claimsrestapi.dto;

import com.claims.claimsrestapi.entity.Note;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimDto {
    private Long id;
    private List<Note> notes;
    private BigDecimal amount;
    private String status;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

}
