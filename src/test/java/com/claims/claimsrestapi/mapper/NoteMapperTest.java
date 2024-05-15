package com.claims.claimsrestapi.mapper;

import com.claims.claimsrestapi.dto.NoteDto;
import com.claims.claimsrestapi.entity.Note;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoteMapperTest {

    @Test
    void testMapToNoteDto() throws ParseException {
        // Initialise Dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdDateTime = dateFormat.parse("2020-3-15 9:00:00");
        Date updatedDateTime = dateFormat.parse("2020-3-16 10:00:00");

        // Create a sample Note object
        Note note = new Note();
        note.setContent("Lorem Ipsum");
        note.setCreatedDateTime(createdDateTime);
        note.setUpdatedDateTime(updatedDateTime);

        // Map Note object to NoteDto
        NoteDto noteDto = NoteMapper.mapToNoteDto(note);

        // Verify that NoteDto is mapped correctly
        assertEquals(note.getId(), noteDto.getId());
        assertEquals(note.getContent(), noteDto.getContent());
        assertEquals(note.getCreatedDateTime(), noteDto.getCreatedDateTime());
        assertEquals(note.getUpdatedDateTime(), noteDto.getUpdatedDateTime());
    }

    @Test
    void testMapToNote() throws ParseException {
        // Initialise Dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdDateTime = dateFormat.parse("2020-3-15 9:00:00");
        Date updatedDateTime = dateFormat.parse("2020-3-16 10:00:00");

        // Create a sample Note object
        NoteDto noteDto = new NoteDto();
        noteDto.setContent("Lorem Ipsum");
        noteDto.setCreatedDateTime(createdDateTime);
        noteDto.setUpdatedDateTime(updatedDateTime);

        // Map NoteDto object to Note
        Note note = NoteMapper.mapToNote(noteDto);

        // Verify that Claim is mapped correctly
        assertEquals(noteDto.getId(), note.getId());
        assertEquals(noteDto.getContent(), note.getContent());
        assertEquals(noteDto.getCreatedDateTime(), note.getCreatedDateTime());
        assertEquals(noteDto.getUpdatedDateTime(), note.getUpdatedDateTime());
    }
}