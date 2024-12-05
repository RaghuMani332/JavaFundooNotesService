package com.fundoo.notesservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fundoo.notesservice.requestdto.CollabratorRequest;
import com.fundoo.notesservice.requestdto.NotesRequest;
import com.fundoo.notesservice.responsedto.NotesResponse;
import com.fundoo.notesservice.service.NotesService;
import com.fundoo.notesservice.util.ResponceStructure;

@RestController
@RequestMapping("api/v1/notes")
public class NotesController {

	
	@Autowired
	private NotesService service;
	
	
	@PostMapping("addNotes/{userId}")
	private ResponseEntity<ResponceStructure<NotesResponse>> addNotes(@PathVariable UUID userId, @RequestBody NotesRequest request)
	{
		return service.addNotes(userId,request);
	}
	
	@GetMapping("getByNoteId/{noteId}")
	private ResponseEntity<ResponceStructure<NotesResponse>> getByNoteId(@PathVariable UUID noteId)
	{
		return service.getByNoteId(noteId);
	}
	
	@GetMapping("getNotes/{userId}")
	private ResponseEntity<ResponceStructure<List<NotesResponse>>> getAllNotes(@PathVariable UUID userId)
	{
		return service.getAllNotes(userId);
	}
	
	@PutMapping("updateNotes")
	private ResponseEntity<ResponceStructure<NotesResponse>> updateNotes(@RequestParam UUID userId,@RequestParam UUID noteId, @RequestBody NotesRequest request)
	{
		return service.updateNotes(userId,noteId,request);
	}
	
	@DeleteMapping("deleteNotes/{noteId}/{userId}")
	private ResponseEntity<ResponceStructure<Boolean>> deleteNotes(@PathVariable UUID noteId,@PathVariable UUID userId)
	{
		return service.deleteNotes(noteId,userId);
	}
	
	@PutMapping("updateTrash/{noteId}/{userId}")
	private ResponseEntity<ResponceStructure<NotesResponse>> updateTrash(@PathVariable UUID noteId,@PathVariable UUID userId)
	{
		return service.updateTrash(noteId,userId);
	}
	
	@PutMapping("updateArchive/{noteId}/{userId}")
	private ResponseEntity<ResponceStructure<NotesResponse>> updateArchive(@PathVariable UUID noteId,@PathVariable UUID userId)
	{
		return service.updateArchive(noteId,userId);
	}
	
	@PutMapping("updatePinned/{noteId}/{userId}")
	private ResponseEntity<ResponceStructure<NotesResponse>> updatePinned(@PathVariable UUID noteId,@PathVariable UUID userId)
	{
		return service.updatePinned(noteId,userId);
	}
//	@PutMapping("addCollabrators")
//	public ResponseEntity<ResponceStructure<NotesResponse>> addCollabrators(@RequestBody CollabratorRequest request) 
//	{
//		return service.addCollacrators(request);
//	}
	
	@PutMapping("updateCollabrators")
	public ResponseEntity<ResponceStructure<NotesResponse>> removeollabrators(@RequestBody CollabratorRequest request) 
	{
		return service.updateCollabrators(request);
	}
	
	
}
