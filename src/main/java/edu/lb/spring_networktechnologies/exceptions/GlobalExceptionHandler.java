package edu.lb.spring_networktechnologies.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


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
