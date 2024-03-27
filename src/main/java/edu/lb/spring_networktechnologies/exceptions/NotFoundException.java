package edu.lb.spring_networktechnologies.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    private NotFoundException(String message) {
        super(message);
    }

    public static ResponseStatusException user() {
        NotFoundException exception = new NotFoundException("User not found");
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }

    public static ResponseStatusException book() {
        NotFoundException exception = new NotFoundException("Book not found");
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }

}
