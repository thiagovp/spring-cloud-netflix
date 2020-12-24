package com.veloso.crud.exception;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = -4147863439065398661L;
	
	private LocalDateTime timestamp;
	private String message;
	private String details;

}
