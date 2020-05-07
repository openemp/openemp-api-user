package org.openemp.api.user.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * User not found exception.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
        super();
    }

	public UserNotFoundException(String username) {
        super("User '" + username + "' not found.");
    }

}
