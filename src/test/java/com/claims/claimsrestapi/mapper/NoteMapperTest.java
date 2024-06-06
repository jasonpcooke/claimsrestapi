package com.claims.claimsrestapi.mapper;

import com.claims.claimsrestapi.dto.NoteDto;
import com.claims.claimsrestapi.entity.Note;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertEquals;

class NoteMapperTest {

    @Test
    void testMapToNoteDto() {

        // Create a sample Note object
        Note note = new Note();
        note.setContent("Lorem Ipsum");

        // Map Note object to NoteDto
        NoteDto noteDto = NoteMapper.mapToNoteDto(note);

        // Verify that NoteDto is mapped correctly
        assertEquals(note.getId(), noteDto.getId());
        assertEquals(note.getContent(), noteDto.getContent());
    }

    @Test
    void testMapToNote() {
        // Create a sample Note object
        NoteDto noteDto = new NoteDto();
        noteDto.setContent("Lorem Ipsum");

        // Map NoteDto object to Note
        Note note = NoteMapper.mapToNote(noteDto);

        // Verify that Claim is mapped correctly
        assertEquals(noteDto.getId(), note.getId());
        assertEquals(noteDto.getContent(), note.getContent());
        assertEquals(noteDto.getCreatedDateTime(), note.getCreatedDateTime());
        assertEquals(noteDto.getUpdatedDateTime(), note.getUpdatedDateTime());
    }
}