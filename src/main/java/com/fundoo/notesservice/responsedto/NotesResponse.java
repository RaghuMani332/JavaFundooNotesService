package com.fundoo.notesservice.responsedto;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotesResponse {

	private UUID noteId;
	private String title;
	private String description;
	private String bgColor;
	private String imagePath;
	private Date remainder;
	private boolean isArchive;
	private boolean isPinned;
	private boolean isTrash ;
	private Date createdAt;
	private Date modifiedAt;
	private Set<UUID> collabId;
	private UUID user;
}
