package com.fundoo.notesservice.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fundoo.notesservice.dao.NotesDao;
import com.fundoo.notesservice.entity.Notes;
import com.fundoo.notesservice.exception.NotesNotFoundException;
import com.fundoo.notesservice.exception.UserMissMatchException;
import com.fundoo.notesservice.exception.UserNotFoundException;
import com.fundoo.notesservice.feing.FeingClient;
import com.fundoo.notesservice.requestdto.CollabratorRequest;
import com.fundoo.notesservice.requestdto.NotesRequest;
import com.fundoo.notesservice.responsedto.NotesResponse;
import com.fundoo.notesservice.responsedto.UserResponse;
import com.fundoo.notesservice.service.NotesService;
import com.fundoo.notesservice.util.ResponceStructure;


@Service
public class NotesServiceImpl implements NotesService{


	@Autowired
	private NotesDao dao;

	@Autowired
	private FeingClient feing;

	@Override
	public ResponseEntity<ResponceStructure<NotesResponse>> addNotes(UUID userId, NotesRequest request) {

		ResponceStructure<UserResponse> user = feing.getUserById(userId).getBody();		

		if(!(user.getStatus() == HttpStatus.OK.value()))
		{
			System.out.println(user.getStatus());
			System.out.println(HttpStatus.OK.value());
			System.out.println(user.getStatus() == HttpStatus.OK.value());
			throw new UserNotFoundException("the given user not found please enter correcrt user id or register the user");
		}

		List<ResponseEntity<ResponceStructure<UserResponse>>> users = request.getCollabId().stream().map(u -> feing.getUserById(u)).toList();

		boolean count = users.stream().filter(u -> u.getBody().getStatus()== HttpStatus.FOUND.value()).count()== users.size();		

		System.out.println(count);

		Notes note=mapToEntity(request, userId); 

		note = dao.save(note);

		NotesResponse response = mapToResponse(note);

		return mapToResponseEntity(response, "added successfully", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponceStructure<List<NotesResponse>>> getAllNotes(UUID userId) {
		
		ResponceStructure<UserResponse> user = feing.getUserById(userId).getBody();		

		if(!(user.getStatus() == HttpStatus.OK.value()))
		{
			throw new UserNotFoundException("the given user not found please enter correcrt user id or register the user");
		}

		List<Notes> notes = dao.getByUserId(userId).orElseThrow(() -> new NotesNotFoundException("no notes are there for given user please add notes for the user"));

		List<Notes> collabedNotes = dao.getByCollabId(userId).get();
		
		notes.addAll(collabedNotes);
		
		List<NotesResponse> responses = notes.stream().map(n -> mapToResponse(n)).toList();

		return mapToResponseEntity(responses, "notes fetched successfully", HttpStatus.OK);

	}

	@Override
	public ResponseEntity<ResponceStructure<NotesResponse>> updateNotes(UUID userId, UUID noteId, NotesRequest request) {

		ResponceStructure<UserResponse> user = feing.getUserById(userId).getBody();
		
		

		if(!(user.getStatus() == HttpStatus.OK.value()))
		{
			throw new UserNotFoundException("the given user not found please enter correcrt user id or register the user");
		}

		List<ResponseEntity<ResponceStructure<UserResponse>>> users = request.getCollabId().stream().map(u -> feing.getUserById(u)).toList();

		boolean count = users.stream().filter(u -> u.getBody().getStatus()== HttpStatus.FOUND.value()).count()== users.size();		

		System.out.println(count);
		

		Notes note = dao.findById(noteId).orElseThrow(() -> new NotesNotFoundException("no notes are there for given noteId please give valid id"));

		if(! (note.getUserId().equals(userId) || note.getCollabId().contains(userId)))
			throw new UserMissMatchException("the user id in notes and provided is missmatching");

		
		Notes entity = mapToEntity(request, userId);

		entity.setCreatedAt(note.getCreatedAt());

		entity.setUserId(note.getUserId());
		
		entity.setId(noteId);

		System.out.println(entity);
		
		note = dao.save(entity);

		NotesResponse response = mapToResponse(note);

		return mapToResponseEntity(response, "notes updated successfully", HttpStatus.OK);

	}

	@Override
	public ResponseEntity<ResponceStructure<Boolean>> deleteNotes(UUID noteId,UUID userId) {

		Notes note = dao.findById(noteId).orElseThrow(() -> new NotesNotFoundException("the given note id is invalid and please give the correct note id"));

		if(!note.getUserId().equals(userId))
			throw new UserMissMatchException("the user id in notes and provided is missmatching");

		dao.delete(note);

		return mapToResponseEntity(true, "deleted successfully", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponceStructure<NotesResponse>> updateTrash(UUID noteId,UUID userId) {
		Notes note = dao.findById(noteId).orElseThrow(() -> new NotesNotFoundException("the given note id is invalid and please give the correct note id"));

		if(!note.getUserId().equals(userId))
			throw new UserMissMatchException("the user id in notes and provided is missmatching");

		note.setTrash(!note.isTrash());

		note.setModifiedAt(new Date());

		note = dao.save(note);

		NotesResponse response = mapToResponse(note);

		return mapToResponseEntity(response, "updated trash successfully", HttpStatus.OK);

	}

	@Override
	public ResponseEntity<ResponceStructure<NotesResponse>> updateArchive(UUID noteId,UUID userId) {

		Notes note = dao.findById(noteId).orElseThrow(() -> new NotesNotFoundException("the given note id is invalid and please give the correct note id"));

		if(!note.getUserId().equals(userId))
			throw new UserMissMatchException("the user id in notes and provided is missmatching");

		note.setArchive(!note.isArchive());

		note.setModifiedAt(new Date());

		note = dao.save(note);

		NotesResponse response = mapToResponse(note);

		return mapToResponseEntity(response, "updated archive successfully", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponceStructure<NotesResponse>> updatePinned(UUID noteId,UUID userId) {

		Notes note = dao.findById(noteId).orElseThrow(() -> new NotesNotFoundException("the given note id is invalid and please give the correct note id"));

		if(!note.getUserId().equals(userId))
			throw new UserMissMatchException("the user id in notes and provided is missmatching");

		note.setPinned(!note.isPinned());

		note.setModifiedAt(new Date());

		note = dao.save(note);

		NotesResponse response = mapToResponse(note);

		return mapToResponseEntity(response, "updated pin notes successfully", HttpStatus.OK);
	}



//	@Override
//	public ResponseEntity<ResponceStructure<NotesResponse>> addCollacrators(CollabratorRequest request) {
//
//		Notes note = dao.findById(request.getNoteId()).orElseThrow(() -> new NotesNotFoundException("the given note id is invalid and please give the correct note id"));
//
//		if(!note.getUserId().equals(request.getUserId()) || note.getCollabId().contains(request.getUserId()))
//			throw new UserMissMatchException("the user id in notes and provided is missmatching");
//
//
//		List<ResponseEntity<ResponceStructure<UserResponse>>> user = request.getCollabratorId().stream().map(u -> feing.getUserById(u)).toList();
//
//		boolean count = user.stream().filter(u -> u.getBody().getStatus()== HttpStatus.OK.value()).count()== user.size();		
//
//		if(count)
//		{
//			note.getCollabId().addAll(request.getCollabratorId());
//
//			note = dao.save(note);
//
//			NotesResponse response = mapToResponse(note);
//
//			return mapToResponseEntity(response, "updated archive successfully", HttpStatus.OK);
//		}
//		throw new UserNotFoundException("ensure all collabrators are registered");
//
//	}

	@Override
	public ResponseEntity<ResponceStructure<NotesResponse>> updateCollabrators(CollabratorRequest request) {
		Notes note = dao.findById(request.getNoteId()).orElseThrow(() -> new NotesNotFoundException("the given note id is invalid and please give the correct note id"));

		if(!(note.getUserId().equals(request.getUserId()) || note.getCollabId().contains(request.getUserId())))
			throw new UserMissMatchException("the user id in notes and provided is missmatching");

		List<ResponseEntity<ResponceStructure<UserResponse>>> user = request.getCollabratorId().stream().map(u -> feing.getUserById(u)).toList();

		boolean count = user.stream().filter(u -> u.getBody().getStatus()== HttpStatus.OK.value()).count()== user.size();		

		
		note.getCollabId().clear();
		note.getCollabId().addAll(request.getCollabratorId());

		note = dao.save(note);

		NotesResponse response = mapToResponse(note);

		return mapToResponseEntity(response, "updated collabrators successfully", HttpStatus.OK);

	}
	
	@Override
	public ResponseEntity<ResponceStructure<NotesResponse>> getByNoteId(UUID noteId) {
		Notes findById = dao.findById(noteId).orElseThrow(() -> new NotesNotFoundException("not is not found for given id"));
		
		NotesResponse response = mapToResponse(findById);
		
		return mapToResponseEntity(response, "notes retrived successfully", HttpStatus.OK);
	}


	private Notes mapToEntity(NotesRequest req,UUID userId)
	{
		return Notes.builder()
				.title(req.getTitle())
				.description(req.getDescription())
				.bgColor(req.getBgColor())
				.imagePath(req.getImagePath())
				.remainder(req.getRemainder())
				.isArchive(req.isArchive())
				.isPinned(req.isPinned())
				.isTrash(false)
				.createdAt(new Date())
				.modifiedAt(new Date())
				.collabId(req.getCollabId())
				.userId(userId)
				.build();
	}

	private NotesResponse mapToResponse(Notes note) {
		return NotesResponse.builder()
				.noteId(note.getId()) 
				.title(note.getTitle())
				.description(note.getDescription())
				.bgColor(note.getBgColor())
				.imagePath(note.getImagePath())
				.remainder(note.getRemainder())
				.isArchive(note.isArchive())
				.isPinned(note.isPinned())
				.isTrash(note.isTrash())
				.createdAt(note.getCreatedAt())
				.modifiedAt(note.getModifiedAt())
				.collabId(note.getCollabId())
				.user(note.getUserId())
				.build();
	}

	private <T> ResponseEntity<ResponceStructure<T>> mapToResponseEntity(T data,String message , HttpStatus status)
	{
		ResponceStructure<T> structure = new ResponceStructure<>();
		structure.setData(data);
		structure.setMessage(message);
		structure.setStatus(status.value());

		return new ResponseEntity<ResponceStructure<T>>(structure,status);


	}

	


}
