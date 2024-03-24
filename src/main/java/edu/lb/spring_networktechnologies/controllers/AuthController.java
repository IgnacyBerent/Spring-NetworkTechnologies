package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.infrastructure.dtos.auth.LoginDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.auth.LoginResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.auth.RegisterDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.auth.RegisterResponseDto;
import edu.lb.spring_networktechnologies.services.AuthService;
import edu.lb.spring_networktechnologies.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterDto requestBody) {
        RegisterResponseDto dto = authService.register(requestBody);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto dto) {
        LoginResponseDto responseDto = authService.login(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

}
