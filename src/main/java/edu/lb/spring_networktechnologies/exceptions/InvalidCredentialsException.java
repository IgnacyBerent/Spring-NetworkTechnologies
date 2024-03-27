package edu.lb.spring_networktechnologies.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidCredentialsException extends RuntimeException {
    private InvalidCredentialsException(String message) {
        super(message);
    }

    public static ResponseStatusException create() {
        InvalidCredentialsException exception = new InvalidCredentialsException("Invalid credentials");
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, exception.getMessage(), exception);
    }

}
