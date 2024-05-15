package com.claims.claimsrestapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import com.claims.claimsrestapi.dto.NoteDto;
import com.claims.claimsrestapi.service.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
}