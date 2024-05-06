package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.exceptions.CheckBindingExceptions;
import edu.lb.spring_networktechnologies.exceptions.UserAlreadyExistsException;
import edu.lb.spring_networktechnologies.infrastructure.dtos.auth.LoginDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.auth.LoginResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.auth.RegisterDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.auth.RegisterResponseDto;
import edu.lb.spring_networktechnologies.services.AuthService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Endpoints for authentication")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Register a new user
     *
     * @param requestBody   - RegisterDto object containing information about the user
     * @param bindingResult - BindingResult object containing information about the validation
     * @return RegisterResponseDto object containing information about the user
     * @throws ResponseStatusException    - if there are any validation errors
     * @throws UserAlreadyExistsException - if the user already exists
     */
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "400", description = "Validation error"),
                    @ApiResponse(responseCode = "409", description = "User already exists"),
                    @ApiResponse(responseCode = "201", description = "User registered", content = @Content),
            }
    )
    public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterDto requestBody, BindingResult bindingResult) {
        CheckBindingExceptions.check(bindingResult);
        RegisterResponseDto dto = authService.register(requestBody);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * Login a user
     *
     * @param dto           - LoginDto object containing information about the user
     * @param bindingResult - BindingResult object containing information about the validation
     * @return LoginResponseDto object containing information about the user
     * @throws ResponseStatusException - if there are any validation errors
     * @throws BadCredentialsException - if the credentials are invalid
     */
    @PostMapping("/login")
    @PreAuthorize("permitAll")
    @SecurityRequirements
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "400", description = "Login failed", content = @Content),
                    @ApiResponse(responseCode = "201", description = "Login success"),
            }
    )
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto dto, BindingResult bindingResult) {
        CheckBindingExceptions.check(bindingResult);
        LoginResponseDto responseDto = authService.login(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

}
