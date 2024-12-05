package com.fundoo.notesservice.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorStructure<T> {

	private int status;
	private String message;
	private T rootCause;
}
