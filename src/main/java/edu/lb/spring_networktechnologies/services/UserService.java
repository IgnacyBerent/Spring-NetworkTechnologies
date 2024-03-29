package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.exceptions.NotFoundException;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.DeleteUserDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.GetUserDto;
import edu.lb.spring_networktechnologies.infrastructure.entities.AuthEntity;
import edu.lb.spring_networktechnologies.infrastructure.entities.UserEntity;
import edu.lb.spring_networktechnologies.infrastructure.repositores.AuthRepository;
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
    private final AuthRepository authRepository;

    @Autowired
    public UserService(UserRepository userRepository, AuthRepository authRepository) {
        this.userRepository = userRepository;
        this.authRepository = authRepository;
    }

    public GetUserDto getUserByUsername(String username) {
        AuthEntity authEntity = authRepository.findByUsername(username).orElseThrow(NotFoundException::user);
        UserEntity userEntity = authEntity.getUser();

        return new GetUserDto(
            userEntity.getId(),
            userEntity.getFirstName(),
            userEntity.getLastName()
        );
    }

    public List<GetUserDto> getAll() {
        var users = userRepository.findAll();

        return StreamSupport.stream(users.spliterator(), false).map(userEntity -> new GetUserDto(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName()
        )).toList();
    }

    public GetUserDto getOne(Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(NotFoundException::user);

        return new GetUserDto(
            userEntity.getId(),
            userEntity.getFirstName(),
            userEntity.getLastName()
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