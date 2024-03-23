package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.infrastructure.dtos.user.GetUserDto;
import edu.lb.spring_networktechnologies.infrastructure.entities.UserEntity;
import edu.lb.spring_networktechnologies.infrastructure.repositores.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    @Value("${jwt.key}")
    private String key;

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<GetUserDto> getAll() {
        var users = userRepository.findAll();

        return StreamSupport.stream(users.spliterator(), false).map(user -> new GetUserDto(
                user.getId(),
                user.getName()
        )).toList();
    }

    public GetUserDto getOne(Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        return new GetUserDto(
            userEntity.getId(),
            userEntity.getName()
        );
    }


    public void delete(Long id) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}