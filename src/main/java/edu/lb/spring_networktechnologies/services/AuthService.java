package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.exceptions.InvalidCredentialsException;
import edu.lb.spring_networktechnologies.exceptions.UserAlreadyExistsException;
import edu.lb.spring_networktechnologies.exceptions.UserNotFoundException;
import edu.lb.spring_networktechnologies.infrastructure.dtos.auth.LoginDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.auth.LoginResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.auth.RegisterDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.auth.RegisterResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.entities.AuthEntity;
import edu.lb.spring_networktechnologies.infrastructure.entities.UserEntity;
import edu.lb.spring_networktechnologies.infrastructure.repositores.AuthRepository;
import edu.lb.spring_networktechnologies.infrastructure.repositores.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Slf4j
public class AuthService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AuthService(AuthRepository authRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public RegisterResponseDto register(RegisterDto registerDto){
        if (authRepository.existsByUsername(registerDto.getUsername())) {
            log.info("User with given username already exists");
            throw UserAlreadyExistsException.byUsername(registerDto.getUsername());
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            log.info("User with given email already exists");
            throw UserAlreadyExistsException.byEmail(registerDto.getEmail());
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registerDto.getEmail());
        userEntity.setFirstName(registerDto.getFirstName());
        userEntity.setLastName(registerDto.getLastName());
        userRepository.save(userEntity);

        AuthEntity authEntity = new AuthEntity();
        authEntity.setUsername(registerDto.getUsername());
        authEntity.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        authEntity.setRole(registerDto.getRole());
        authEntity.setUser(userEntity);

        authRepository.save(authEntity);
        return new RegisterResponseDto(userEntity.getId(), authEntity.getUsername(), authEntity.getRole());
    }

    public LoginResponseDto login(LoginDto loginDto){
        Optional<AuthEntity> authEntity = authRepository
                .findByUsername(loginDto.getUsername());

        if (authEntity.isEmpty()) {
            log.info("User with given username not found");
            throw UserNotFoundException.create();
        }

        if (!passwordEncoder.matches(loginDto.getPassword(), authEntity.get().getPassword())) {
            throw InvalidCredentialsException.create();
        }

        String token = jwtService.generateToken(authEntity.get());
        return new LoginResponseDto(token);
    }
}
