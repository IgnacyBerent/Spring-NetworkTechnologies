package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.infrastructure.dtos.user.UserCreateDTO;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.UserDTO;
import edu.lb.spring_networktechnologies.infrastructure.entities.User;
import edu.lb.spring_networktechnologies.infrastructure.repositores.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User toUser(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        user.setRole(userCreateDTO.getRole());
        user.setEmail(userCreateDTO.getEmail());
        user.setName(userCreateDTO.getName());
        return user;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }

    public Iterable<UserDTO> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(toUserDTO(user));
        }
        return userDTOs;
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

}