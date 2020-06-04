package org.openemp.api.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * User disabled exception.
 */
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class UserDisabledException extends RuntimeException{
	
	private static final long serialVersionUID = 6041862734673448645L;

	public UserDisabledException() {
        super();
    }

	public UserDisabledException(String username) {
        super("User " + username + "is Disabled");
    }
}
