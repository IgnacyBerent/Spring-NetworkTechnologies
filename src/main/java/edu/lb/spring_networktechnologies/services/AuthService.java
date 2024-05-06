package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.exceptions.UserAlreadyExistsException;
import edu.lb.spring_networktechnologies.infrastructure.dtos.auth.LoginDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.auth.LoginResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.auth.RegisterDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.auth.RegisterResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.entities.AuthEntity;
import edu.lb.spring_networktechnologies.infrastructure.entities.UserEntity;
import edu.lb.spring_networktechnologies.infrastructure.repositores.AuthRepository;
import edu.lb.spring_networktechnologies.infrastructure.repositores.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
     *
     * @param registerDto - DTO object containing user data
     * @return RegisterResponseDto - DTO object containing non-sensitive user data
     * @throws UserAlreadyExistsException - if user with given username or email already exists
     */
    @Transactional
    public RegisterResponseDto register(RegisterDto registerDto) {
        if (authRepository.existsByUsername(registerDto.getUsername())) {
            throw new UserAlreadyExistsException("User with username " + registerDto.getUsername() + " already exists");
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + registerDto.getEmail() + " already exists");
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
     * This method is used to log in a user
     * It checks if user with given username exists and if password is correct
     * If everything is correct, it generates JWT token
     *
     * @param loginDto - DTO object containing user credentials
     * @return LoginResponseDto - DTO object containing JWT token
     * @throws EntityNotFoundException - if user with given username not found
     * @throws BadCredentialsException - if password is incorrect
     */
    public LoginResponseDto login(LoginDto loginDto) {
        var authEntity = authRepository
                .findByUsername(loginDto.getUsername()).orElseThrow(
                        () -> new EntityNotFoundException("User not found")
                );


        if (!passwordEncoder.matches(loginDto.getPassword(), authEntity.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }

        String token = jwtService.generateToken(authEntity);
        return new LoginResponseDto(token);
    }
}
