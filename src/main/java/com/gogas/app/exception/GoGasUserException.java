package com.gogas.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@SuppressWarnings("serial")
public class GoGasUserException extends ResponseStatusException {

	public GoGasUserException(HttpStatus status, String reason) {
		super(status, reason);
	}

	
}
