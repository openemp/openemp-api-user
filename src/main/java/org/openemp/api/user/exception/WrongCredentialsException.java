package org.openemp.api.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Wrong credentials exception.
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class WrongCredentialsException extends RuntimeException {
	
	private static final long serialVersionUID = -2958259497970846376L;

	/**
	 * Instantiates a new Wrong credentials exception.
	 */
	public WrongCredentialsException(){}

}
