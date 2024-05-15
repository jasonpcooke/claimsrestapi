package com.claims.claimsrestapi.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import com.claims.claimsrestapi.dto.NoteDto;
import com.claims.claimsrestapi.entity.Note;
import com.claims.claimsrestapi.exception.ResourceNotFoundException;
import com.claims.claimsrestapi.mapper.NoteMapper;
import com.claims.claimsrestapi.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteServiceImpl noteService;

    @Test
    void testCreateNote() {
        // Given
        NoteDto noteDto = new NoteDto(); // create a sample NoteDto
        Note note = NoteMapper.mapToNote(noteDto); // map NoteDto to Note
        when(noteRepository.save(any(Note.class))).thenReturn(note); // mock noteRepository.save to return the note

        // When
        NoteDto result = noteService.createNote(noteDto);

        // Then
        assertTrue(new ReflectionEquals(noteDto).matches(result)); // verify that the result matches the input noteDto
        verify(noteRepository, times(1)).save(any(Note.class)); // verify that noteRepository.save was called once with any Note object
    }

    @Test
    void testGetNoteById_ExistingNote() {
        // Given
        Long noteId = 1L;
        Note note = new Note();
        note.setId(noteId); // set a dummy note's Id to noteId
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note)); // When findById is called using noteId, return an Optional containing the specified note

        // When
        NoteDto result = noteService.getNoteById(noteId);

        // Then
        assertNotNull(result); // Assert that a Dto has been mapped with a nonnull result
        assertEquals(noteId, result.getId()); // Assert that the id of the result matches the dummy note's id
        verify(noteRepository, times(1)).findById(noteId); // Verify that findById was invoked once
    }

    @Test
    void testGetNoteById_NonExistingNote() {
        // Given
        Long noteId = 1L;
        when(noteRepository.findById(noteId)).thenReturn(Optional.empty()); // Return an empty Optional as a dummy note has not been instantiated

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> noteService.getNoteById(noteId)); // Assert that an exception has been thrown due to the note service not finding a note with the provided noteId
        verify(noteRepository, times(1)).findById(noteId); // Verify that findById was invoked once
    }
}