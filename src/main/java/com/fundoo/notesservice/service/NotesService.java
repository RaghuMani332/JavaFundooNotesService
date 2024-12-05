package com.fundoo.notesservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.fundoo.notesservice.requestdto.CollabratorRequest;
import com.fundoo.notesservice.requestdto.NotesRequest;
import com.fundoo.notesservice.responsedto.NotesResponse;
import com.fundoo.notesservice.util.ResponceStructure;

public interface NotesService {

	ResponseEntity<ResponceStructure<NotesResponse>> addNotes(UUID userId, NotesRequest request);

	ResponseEntity<ResponceStructure<List<NotesResponse>>> getAllNotes(UUID userId);

	ResponseEntity<ResponceStructure<NotesResponse>> updateNotes(UUID userId, UUID noteId, NotesRequest request);

	ResponseEntity<ResponceStructure<Boolean>> deleteNotes(UUID noteId,UUID userId);

	ResponseEntity<ResponceStructure<NotesResponse>> updateTrash(UUID noteId, UUID userId);

	ResponseEntity<ResponceStructure<NotesResponse>> updateArchive(UUID noteId, UUID userId);

	ResponseEntity<ResponceStructure<NotesResponse>> updatePinned(UUID noteId,UUID userId);

//	ResponseEntity<ResponceStructure<NotesResponse>> addCollacrators(CollabratorRequest request);

	ResponseEntity<ResponceStructure<NotesResponse>> updateCollabrators(CollabratorRequest request);

	ResponseEntity<ResponceStructure<NotesResponse>> getByNoteId(UUID noteId);

}
