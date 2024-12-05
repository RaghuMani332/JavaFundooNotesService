package com.fundoo.notesservice.requestdto;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class NotesRequest {

	 	private String title;
	    private String description;
	    private String bgColor;
	    private String imagePath;
	    private Date remainder;
	    private boolean isArchive ;
	    private boolean isPinned;
	    private Set<UUID> collabId;
}
