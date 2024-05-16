package com.claims.claimsrestapi.controller;

import com.claims.claimsrestapi.dto.NoteDto;
import com.claims.claimsrestapi.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private NoteService noteService;

    //  Build Add Note ReST API
    @PostMapping
    public ResponseEntity<NoteDto> createNote(@RequestBody NoteDto noteDto) {
        NoteDto savedNote = noteService.createNote(noteDto);
        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }

    //  Build Get Note ReST API
    @GetMapping("{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable("id") Long noteId){
        NoteDto noteDto = noteService.getNoteById(noteId);
        return ResponseEntity.ok(noteDto);
    }

    //  Build Get All Notes ReST API
    @GetMapping
    public ResponseEntity<List<NoteDto>> getAllNotes(){
        List<NoteDto> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    // Build Update Note ReST API
    @PutMapping("{id}")
    public ResponseEntity<NoteDto> updatedNote(@PathVariable("id") Long noteId,
                                                 @RequestBody NoteDto updatedNote){
        NoteDto noteDto = noteService.updateNote(noteId, updatedNote);
        return ResponseEntity.ok(noteDto);
    }

    // Build Delete Note ReST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteNote(@PathVariable("id") Long noteID){
        noteService.deleteNote(noteID);
        return ResponseEntity.ok("Note deleted successfully.");
    }
}
