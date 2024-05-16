package com.claims.claimsrestapi.service;

import com.claims.claimsrestapi.dto.NoteDto;

import java.util.List;

public interface NoteService {
    NoteDto createNote(NoteDto noteDto);
    NoteDto getNoteById(Long noteId);
    List<NoteDto> getAllNotes();
}