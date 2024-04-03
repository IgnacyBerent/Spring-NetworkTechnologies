package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.exceptions.InvalidCredentialsException;
import edu.lb.spring_networktechnologies.exceptions.AlreadyExistsException;
import edu.lb.spring_networktechnologies.exceptions.NotFoundException;
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

    /**
     * This method is used to register a new user by admin
     * @param registerDto - DTO object containing user data
     * @return RegisterResponseDto - DTO object containing non-sensitive user data
     * @throws AlreadyExistsException - if user with given username or email already exists
     */
    @Transactional
    public RegisterResponseDto register(RegisterDto registerDto){
        if (authRepository.existsByUsername(registerDto.getUsername())) {
            log.info("User with given username already exists");
            throw AlreadyExistsException.userByUsername(registerDto.getUsername());
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            log.info("User with given email already exists");
            throw AlreadyExistsException.userByEmail(registerDto.getEmail());
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

    /**
     * This method is used to login a user
     * It checks if user with given username exists and if password is correct
     * If everything is correct, it generates JWT token
     * @param loginDto - DTO object containing user credentials
     * @return LoginResponseDto - DTO object containing JWT token
     * @throws NotFoundException - if user with given username not found
     */
    public LoginResponseDto login(LoginDto loginDto){
        Optional<AuthEntity> authEntity = authRepository
                .findByUsername(loginDto.getUsername());

        if (authEntity.isEmpty()) {
            log.info("User with given username not found");
            throw NotFoundException.user();
        }

        if (!passwordEncoder.matches(loginDto.getPassword(), authEntity.get().getPassword())) {
            log.info("Invalid credentials");
            throw InvalidCredentialsException.create();
        }

        String token = jwtService.generateToken(authEntity.get());
        return new LoginResponseDto(token);
    }
}
