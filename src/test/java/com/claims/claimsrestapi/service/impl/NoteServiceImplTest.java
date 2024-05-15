package com.claims.claimsrestapi.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import com.claims.claimsrestapi.dto.NoteDto;
import com.claims.claimsrestapi.entity.Note;
import com.claims.claimsrestapi.mapper.NoteMapper;
import com.claims.claimsrestapi.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;

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
}