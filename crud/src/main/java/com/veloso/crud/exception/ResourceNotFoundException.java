package com.veloso.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	
	private static final String ERROR_MESSAGE = "No records found for this ID";
	

	private static final long serialVersionUID = 586949774506498940L;


	public ResourceNotFoundException() {
		super(ERROR_MESSAGE);
	}
	
	
}
