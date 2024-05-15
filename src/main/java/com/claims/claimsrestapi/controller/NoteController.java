package com.claims.claimsrestapi.controller;

import com.claims.claimsrestapi.dto.NoteDto;
import com.claims.claimsrestapi.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private NoteService noteService;

    //Build Add Note ReST API
    @PostMapping
    public ResponseEntity<NoteDto> createNote(@RequestBody NoteDto noteDto) {
        NoteDto savedNote = noteService.createNote(noteDto);
        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }
}