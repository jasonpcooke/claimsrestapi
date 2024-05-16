package com.claims.claimsrestapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.claims.claimsrestapi.dto.NoteDto;
import com.claims.claimsrestapi.service.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class NoteControllerTest {

    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteController noteController;


    @Test
    void testCreateNote() {
        // Given
        NoteDto noteDto = new NoteDto(); // create a sample NoteDto
        NoteDto savedNoteDto = new NoteDto(); // create a sample saved NoteDto
        when(noteService.createNote(any(NoteDto.class))).thenReturn(savedNoteDto); // mock noteService.createNote to return the saved note

        // When
        ResponseEntity<NoteDto> responseEntity = noteController.createNote(noteDto);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()); // verify the status code
        assertEquals(savedNoteDto, responseEntity.getBody()); // verify that the response body matches the saved note
        verify(noteService, times(1)).createNote(any(NoteDto.class)); // verify that noteService.createNote was called once with any NoteDto object
    }

    @Test
    void testGetNote() {
        // Given
        Long noteId = 1L;

        // When
        ResponseEntity<NoteDto> responseEntity = noteController.getNoteById(noteId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(noteService).getNoteById(noteId);
    }

    @Test
    void testGetAllNotes() throws Exception {
        // Given
        List<NoteDto> mockNotes = new ArrayList<>();
        NoteDto note1 = new NoteDto();
        note1.setId(1L);
        mockNotes.add(note1);
        NoteDto note2 = new NoteDto();
        note2.setId(2L);
        mockNotes.add(note2);

        // When
        when(noteService.getAllNotes()).thenReturn(mockNotes); // Mocking noteService.getAllNotes() to return mockNotes

        // Setting up MockMvc
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();

        // Then
        mockMvc.perform(get("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(mockNotes.size()))
                .andExpect(jsonPath("$[0].id").value(note1.getId()))
                .andExpect(jsonPath("$[1].id").value(note2.getId()));
        verify(noteService, times(1)).getAllNotes(); // Verifying that getAllNotes method of noteService is called exactly once
    }

    @Test
    void testUpdatedNote() {
        // Given
        Long noteId = 1L;
        NoteDto updatedNoteDto = new NoteDto();
        updatedNoteDto.setContent("Lorem ipsum dolor");

        NoteDto mockNoteDto = new NoteDto();
        mockNoteDto.setId(noteId);
        mockNoteDto.setContent("Lorem ipsum");

        when(noteService.updateNote(noteId, updatedNoteDto)).thenReturn(mockNoteDto);

        // When
        ResponseEntity<NoteDto> responseEntity = noteController.updatedNote(noteId, updatedNoteDto);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockNoteDto, responseEntity.getBody());
    }

    @Test
    void testDeleteNote() {
        // Given
        Long noteId = 1L;

        // When
        ResponseEntity<String> responseEntity = noteController.deleteNote(noteId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Note deleted successfully.", responseEntity.getBody());
        verify(noteService, times(1)).deleteNote(noteId);
    }
}