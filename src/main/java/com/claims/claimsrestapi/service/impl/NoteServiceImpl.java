package com.claims.claimsrestapi.service.impl;

import com.claims.claimsrestapi.dto.NoteDto;
import com.claims.claimsrestapi.entity.Note;
import com.claims.claimsrestapi.exception.CreatedAndUpdatedDateTimeException;
import com.claims.claimsrestapi.exception.ResourceNotFoundException;
import com.claims.claimsrestapi.mapper.NoteMapper;
import com.claims.claimsrestapi.repository.NoteRepository;
import com.claims.claimsrestapi.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    @Override
    public NoteDto createNote(NoteDto noteDto) {

        Note note = NoteMapper.mapToNote(noteDto);
        Note savedNote = noteRepository.save(note);
        return NoteMapper.mapToNoteDto(savedNote);
    }

    @Override
    public NoteDto getNoteById(Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Note does not exist with given id: " + noteId));

        return NoteMapper.mapToNoteDto(note);
    }

    @Override
    public List<NoteDto> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        return notes.stream()
                .map(NoteMapper::mapToNoteDto)
                .toList();
    }

    @Override
    public NoteDto updateNote(Long noteId, NoteDto updatedNote) {
        Note note = noteRepository.findById(noteId).orElseThrow(
                () -> new ResourceNotFoundException("Note does not exist with given ID: " + noteId)
        );
        if (note.getCreatedDateTime().after(updatedNote.getUpdatedDateTime())) {
            throw new CreatedAndUpdatedDateTimeException("UpdatedDateTime cannot be before CreatedDateTime");
        }
        if (updatedNote.getContent() != null) {
            note.setContent(updatedNote.getContent());
        }
        if (updatedNote.getUpdatedDateTime() != null) {
            note.setUpdatedDateTime(updatedNote.getUpdatedDateTime());
        }
        Note updatedNoteObj = noteRepository.save(note);

        return NoteMapper.mapToNoteDto(updatedNoteObj);
    }

    @Override
    public void deleteNote(Long noteId) {
        noteRepository.findById(noteId).orElseThrow(
                () -> new ResourceNotFoundException("Note does not exist with given ID: " + noteId)
        );

        noteRepository.deleteById((noteId));
    }
}
