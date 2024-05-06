package edu.lb.spring_networktechnologies.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ExpiredJwtException.class, JwtException.class, TokenBlacklistedException.class})
    public ResponseEntity<ProblemDetail> handleJwtExceptions(Exception e) {
        log.error("JWT exception", e);
        return buildResponseEntity(HttpStatusCode.valueOf(401), e.getMessage());
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<ProblemDetail> handleAuthenticationExceptions(Exception e) {
        log.error("Authentication exception", e);
        return buildResponseEntity(HttpStatusCode.valueOf(403), e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        log.error("User already exists", e);
        return buildResponseEntity(HttpStatusCode.valueOf(409), e.getMessage());
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleBookAlreadyExistsException(BookAlreadyExistsException e) {
        log.error("Book already exists", e);
        return buildResponseEntity(HttpStatusCode.valueOf(409), e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("Entity not found", e);
        return buildResponseEntity(HttpStatusCode.valueOf(404), e.getMessage());
    }

    @ExceptionHandler(NoBookInStockException.class)
    public ResponseEntity<ProblemDetail> handleNoBookInStockException(NoBookInStockException e) {
        log.error("No book in stock", e);
        return buildResponseEntity(HttpStatusCode.valueOf(404), e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ProblemDetail> handleIllegalStateException(IllegalStateException e) {
        log.error("Illegal state", e);
        return buildResponseEntity(HttpStatusCode.valueOf(400), e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> handleAccessDeniedException(AccessDeniedException e) {
        log.error("Access denied", e);
        return buildResponseEntity(HttpStatusCode.valueOf(403), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Validation error", e);
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("\n"));
        return buildResponseEntity(HttpStatusCode.valueOf(400), message);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ProblemDetail> handleResponseStatusException(ResponseStatusException e) {
        log.error("Response status exception", e);
        return buildResponseEntity(HttpStatusCode.valueOf(e.getStatusCode().value()), e.getReason());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception e) {
        log.error("Unhandled exception", e);
        return buildResponseEntity(HttpStatusCode.valueOf(500), "Internal server error");
    }

    private ResponseEntity<ProblemDetail> buildResponseEntity(HttpStatusCode status, String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        return new ResponseEntity<>(problemDetail, status);
    }
}
