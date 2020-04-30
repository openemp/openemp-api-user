package org.openemp.api.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class UserDisabledException extends RuntimeException{
    public UserDisabledException() {
        super();
    }

    public UserDisabledException(String username) {
        super("User " + username + "is Disabled");
    }
}
