package com.fundoo.notesservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotesNotFoundException extends RuntimeException {

	private String message;
}
