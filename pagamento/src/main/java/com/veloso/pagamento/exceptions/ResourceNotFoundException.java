package com.veloso.pagamento.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6367009842650967132L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	

}
