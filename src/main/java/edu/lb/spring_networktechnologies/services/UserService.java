package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.infrastructure.dtos.user.GetUserDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.UpdateUserDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.UpdateUserResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.entities.AuthEntity;
import edu.lb.spring_networktechnologies.infrastructure.entities.UserEntity;
import edu.lb.spring_networktechnologies.infrastructure.mappings.MapUser;
import edu.lb.spring_networktechnologies.infrastructure.repositores.AuthRepository;
import edu.lb.spring_networktechnologies.infrastructure.repositores.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService extends OwnershipService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, AuthRepository authRepository) {
        super(authRepository);
        this.userRepository = userRepository;
    }

    /**
     * Method for getting a single user by its username
     *
     * @param username - username of the user
     * @return GetUserDto object containing information about the user
     * @throws EntityNotFoundException - if user with given username does not exist
     */
    @PostAuthorize("hasRole('ADMIN') or isAuthenticated() and this.isOwner(authentication.name, #returnObject.id)")
    public GetUserDto getUserByUsername(String username) {
        AuthEntity authEntity = authRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User with username " + username + " does not exist"));
        UserEntity userEntity = authEntity.getUser();

        return MapUser.toGetUserDto(userEntity);
    }

    /**
     * Method for getting all users from the database
     *
     * @return List of GetUserDto objects containing information about the users
     */
    public List<GetUserDto> getAll() {
        var users = userRepository.findAll();

        return users.stream().map(MapUser::toGetUserDto).toList();
    }

    /**
     * Method for getting a single user by its id
     *
     * @param id - id of the user
     * @return GetUserDto object containing information about the user
     * @throws EntityNotFoundException - if user with given id does not exist
     */
    public GetUserDto getOne(Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " does not exist"));

        return MapUser.toGetUserDto(userEntity);
    }

    /**
     * Method for deleting a single user by its id
     *
     * @param id - id of the user
     * @throws EntityNotFoundException - if user with given id does not exist
     */
    @PreAuthorize("hasRole('ADMIN') or isAuthenticated() and this.isOwner(authentication.name, #id)")
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            log.info("User with given id not found");
            throw new EntityNotFoundException("User with id " + id + " does not exist");
        }
        userRepository.deleteById(id);
    }

    /**
     * Method for updating a single user by its id
     *
     * @param id  - id of the user
     * @param dto - UpdateUserDto object containing information about the user
     * @return UpdateUserResponseDto object containing information about the updated user
     * @throws EntityNotFoundException - if user with given id does not exist
     */
    @PostAuthorize("hasRole('ADMIN') or isAuthenticated() and this.isOwner(authentication.name, #id)")
    public UpdateUserResponseDto update(Long id, UpdateUserDto dto) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " does not exist"));

        dto.getfName().ifPresent(userEntity::setFirstName);
        dto.getlName().ifPresent(userEntity::setLastName);
        dto.getEmail().ifPresent(userEntity::setEmail);

        userRepository.save(userEntity);

        return new UpdateUserResponseDto(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail()
        );
    }
}