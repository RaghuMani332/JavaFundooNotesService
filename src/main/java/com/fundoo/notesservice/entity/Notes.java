package com.fundoo.notesservice.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
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
    
    @ElementCollection
    private Set<UUID> collabId;
    
    private UUID userId;
    
    }
