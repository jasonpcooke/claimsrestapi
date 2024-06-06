package com.claims.claimsrestapi.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.claims.claimsrestapi.dto.NoteDto;
import com.claims.claimsrestapi.entity.Note;
import com.claims.claimsrestapi.exception.ResourceNotFoundException;
import com.claims.claimsrestapi.mapper.NoteMapper;
import com.claims.claimsrestapi.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
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

        // When
        when(noteRepository.findById(noteId)).thenReturn(Optional.empty()); // Return an empty Optional as a dummy note has not been instantiated

        // Then
        assertThrows(
                ResourceNotFoundException.class,
                () -> noteService.getNoteById(noteId)); // Assert that an exception has been thrown due to the note service not finding a note with the provided noteId
        verify(noteRepository, times(1)).findById(noteId); // Verify that findById was invoked once
    }

    @Test
    void testGetAllNotes() {
        // Given
        List<Note> mockNotes = new ArrayList<>();
        Note note1 = new Note();
        note1.setId(1L);
        mockNotes.add(note1);
        Note note2 = new Note();
        note2.setId(2L);
        mockNotes.add(note2);

        when(noteRepository.findAll()).thenReturn(mockNotes); // Mocking noteRepository.findAll() to return mockNotes

        // When
        List<NoteDto> result = noteService.getAllNotes(); // Calling the method under test

        // Then
        assertNotNull(result); // Asserting that the result is not null
        assertEquals(2, result.size()); // Asserting that the size of the result list is 2
        assertEquals(1L, result.get(0).getId()); // Asserting the first note ID in the result list
        assertEquals(2L, result.get(1).getId()); // Asserting the second note ID in the result list
        verify(noteRepository, times(1)).findAll(); // Verifying that findAll method of noteRepository is called exactly once
    }

    @Test
    void testUpdateNote() {
        // Given
        Long noteId = 1L;

        NoteDto updatedNote = new NoteDto();
        updatedNote.setContent("Lorem ipsum dolor");

        Note note = new Note();
        note.setId(noteId);
        note.setContent("Lorem ipsum");

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));
        when(noteRepository.save(any())).thenReturn(note);

        // When
        NoteDto result = noteService.updateNote(noteId, updatedNote);

        // Then
        assertEquals(noteId, result.getId());
        assertEquals(updatedNote.getContent(), result.getContent());

        ArgumentCaptor<Note> captor = ArgumentCaptor.forClass(Note.class);
        verify(noteRepository).save(captor.capture());
        assertEquals(noteId, captor.getValue().getId());
        assertEquals(updatedNote.getContent(), captor.getValue().getContent());
        assertEquals(updatedNote.getUpdatedDateTime(), captor.getValue().getUpdatedDateTime());
    }

    @Test
    void testUpdateNote_ResourceNotFoundException() {
        // Given
        Long noteId = 1L;

        NoteDto updatedNote = new NoteDto();
        updatedNote.setContent("Lorem ipsum dolor");
        //When
        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());


        // Then
        assertThrows(
                ResourceNotFoundException.class,
                () -> noteService.updateNote(noteId, updatedNote)); // Assert that an exception has been thrown due to the note service not finding a note with the provided noteId
        verify(noteRepository, times(1)).findById(noteId); // Verify that findById was invoked once
        verify(noteRepository, never()).save(any());
    }

    @Test
    void testDeleteNote_Success() {
        // Given
        Long noteId = 1L;
        when(noteRepository.findById(noteId)).thenReturn(java.util.Optional.of(new Note()));

        // When
        noteService.deleteNote(noteId);

        // Then
        verify(noteRepository, times(1)).deleteById(noteId);
    }

    @Test
    void testDeleteNote_NoteNotFound() {
        // Given
        Long noteId = 1L;
        // When
        when(noteRepository.findById(noteId)).thenReturn(java.util.Optional.empty());

        // Then
        assertThrows(
                ResourceNotFoundException.class, 
                () -> noteService.deleteNote(noteId));
        verify(noteRepository, never()).deleteById(noteId);
    }
}