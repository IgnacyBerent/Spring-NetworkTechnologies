package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.infrastructure.entities.AuthEntity;
import edu.lb.spring_networktechnologies.infrastructure.repositores.AuthRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnershipService {
    protected final AuthRepository authRepository;

    @Autowired
    public OwnershipService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    /**
     * Method for checking if the user is the owner of the entity
     *
     * @param username - username of the user
     * @param userId   - id of the user
     * @return true if the user is the owner of the entity, false otherwise
     * @throws EntityNotFoundException - if user with given username does not exist
     */
    public boolean isOwner(String username, Long userId) {
        if (userId == null || username == null) {
            return false;
        }

        AuthEntity authEntity = authRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User with username " + username + " does not exist"));
        return userId.equals(authEntity.getUser().getId());
    }
}
