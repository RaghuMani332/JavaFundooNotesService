package com.fundoo.notesservice.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundoo.notesservice.entity.Notes;

public interface NotesDao extends JpaRepository<Notes, UUID>{

	Optional<List<Notes>> getByUserId(UUID userId);

	Optional<List<Notes>> getByCollabId(UUID userId);

}
