package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.exceptions.NotFoundException;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.DeleteUserDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.GetUserDto;
import edu.lb.spring_networktechnologies.infrastructure.repositores.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<GetUserDto> getAll() {
        var users = userRepository.findAll();

        return StreamSupport.stream(users.spliterator(), false).map(user -> new GetUserDto(
                user.getId(),
                user.getFirstName()
        )).toList();
    }

    public GetUserDto getOne(Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        return new GetUserDto(
            userEntity.getId(),
            userEntity.getFirstName()
        );
    }


    public DeleteUserDto delete(Long id) {
        if(!userRepository.existsById(id)) {
            log.info("User with given id not found");
            throw NotFoundException.user();
        }
        userRepository.deleteById(id);
        return new DeleteUserDto(id);
    }
}