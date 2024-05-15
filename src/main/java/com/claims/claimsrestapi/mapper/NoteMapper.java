package com.claims.claimsrestapi.mapper;

import com.claims.claimsrestapi.dto.NoteDto;
import com.claims.claimsrestapi.entity.Note;

public class NoteMapper {

    public static NoteDto mapToNoteDto(Note note){
        return new NoteDto(
                note.getId(),
                note.getContent(),
                note.getCreatedDateTime(),
                note.getUpdatedDateTime()
        );
    }

    public static Note mapToNote(NoteDto noteDto){
        return new Note(
                noteDto.getId(),
                noteDto.getContent(),
                noteDto.getCreatedDateTime(),
                noteDto.getUpdatedDateTime()
        );
    }
}
