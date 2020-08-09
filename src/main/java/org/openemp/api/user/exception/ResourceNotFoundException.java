package org.openemp.api.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Resource not found exception.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8644988819321111449L;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String id) {
        super("Resource with id: '" + id + "' not found.");
    }

}
