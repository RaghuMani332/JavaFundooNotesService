package com.fundoo.notesservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserServiceNotAvailableException extends RuntimeException {

	private String message;
}
