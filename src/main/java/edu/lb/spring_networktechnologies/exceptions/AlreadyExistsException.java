package edu.lb.spring_networktechnologies.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends RuntimeException {
    private AlreadyExistsException(String message) {
        super(message);
    }

    public static ResponseStatusException userByUsername(String username) {
        AlreadyExistsException exception = new AlreadyExistsException("User with username: " + username + " already exists");
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }

    public static ResponseStatusException userByEmail(String email) {
        AlreadyExistsException exception = new AlreadyExistsException("User with email: " + email + " already exists");
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }

    public static ResponseStatusException bookByIsbn(String isbn) {
        AlreadyExistsException exception = new AlreadyExistsException("Book with isbn: " + isbn + " already exists");
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }

}
