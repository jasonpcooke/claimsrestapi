package com.claims.claimsrestapi.dto;

import com.claims.claimsrestapi.entity.Claim;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {
    private Long id;
    private Claim claim;
    private String content;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
}
