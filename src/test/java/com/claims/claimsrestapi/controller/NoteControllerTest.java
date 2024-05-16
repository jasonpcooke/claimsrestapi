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
        NoteDto savedNoteDto = new NoteDto(); // create a sample saved ClaimDto
        when(noteService.createNote(any(NoteDto.class))).thenReturn(savedNoteDto); // mock claimService.createClaim to return the saved claim

        // When
        ResponseEntity<NoteDto> responseEntity = noteController.createNote(noteDto);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()); // verify the status code
        assertEquals(savedNoteDto, responseEntity.getBody()); // verify that the response body matches the saved claim
        verify(noteService, times(1)).createNote(any(NoteDto.class)); // verify that claimService.createClaim was called once with any ClaimDto object
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
    void testGetAllClaims() throws Exception {
        // Given
        List<NoteDto> mockNotes = new ArrayList<>();
        NoteDto note1 = new NoteDto();
        note1.setId(1L);
        mockNotes.add(note1);
        NoteDto note2 = new NoteDto();
        note2.setId(2L);
        mockNotes.add(note2);

        when(noteService.getAllNotes()).thenReturn(mockNotes); // Mocking noteService.getAllNotes() to return mockNotes

        // Setting up MockMvc
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();

        // When & Then
        mockMvc.perform(get("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(mockNotes.size()))
                .andExpect(jsonPath("$[0].id").value(note1.getId()))
                .andExpect(jsonPath("$[1].id").value(note2.getId()));
        verify(noteService, times(1)).getAllNotes(); // Verifying that getAllNotes method of noteService is called exactly once
    }
}