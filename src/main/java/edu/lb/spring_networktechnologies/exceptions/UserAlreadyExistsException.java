package edu.lb.spring_networktechnologies.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExistsException extends RuntimeException {
    private UserAlreadyExistsException(String message) {
        super(message);
    }

    public static ResponseStatusException byUsername(String username) {
        UserAlreadyExistsException exception = new UserAlreadyExistsException("User with username: " + username + " already exists");
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }

    public static ResponseStatusException byEmail(String email) {
        UserAlreadyExistsException exception = new UserAlreadyExistsException("User with email: " + email + " already exists");
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }

}
