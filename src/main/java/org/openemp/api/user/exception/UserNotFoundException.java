package org.openemp.api.user.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * User not found exception.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -8861027770836259171L;

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String uuid) {
        super("User with uuid '" + uuid + "' not found.");
    }

}
