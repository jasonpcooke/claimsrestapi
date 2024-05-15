package com.claims.claimsrestapi.service;

import com.claims.claimsrestapi.dto.NoteDto;

public interface NoteService {
    NoteDto createNote(NoteDto noteDto);
}