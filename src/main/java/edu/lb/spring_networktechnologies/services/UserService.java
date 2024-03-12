package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.infrastructure.dtos.user.CreateUserDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.CreateUserResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.GetUserDto;
import edu.lb.spring_networktechnologies.infrastructure.entities.UserEntity;
import edu.lb.spring_networktechnologies.infrastructure.repositores.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<GetUserDto> getAll() {
        var users = userRepository.findAll();

        return StreamSupport.stream(users.spliterator(), false).map(user -> new GetUserDto(
                user.getId(),
                user.getUsername()
        )).toList();
    }

    public GetUserDto getOne(Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        return new GetUserDto(
            userEntity.getId(),
            userEntity.getUsername()
        );
    }

    public CreateUserResponseDto create(CreateUserDto user) {
        var userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setRole(user.getRole());
        userEntity.setEmail(user.getEmail());
        userEntity.setName(user.getName());
        userEntity.setLoans(new ArrayList<>());
        userEntity.setReviews(new ArrayList<>());

        var newUser = userRepository.save(userEntity);

        return new CreateUserResponseDto(
            newUser.getId(),
            newUser.getUsername(),
            newUser.getPassword(),
            newUser.getRole(),
            newUser.getEmail(),
            newUser.getName()
        );
    }

    public void delete(Long id) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException("Book not found");
        }
        userRepository.deleteById(id);
    }
}