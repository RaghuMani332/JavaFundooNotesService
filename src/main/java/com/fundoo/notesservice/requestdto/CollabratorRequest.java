package com.fundoo.notesservice.requestdto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollabratorRequest {

	private UUID userId;
	private UUID noteId;
	private Set<UUID> collabratorId;
}
