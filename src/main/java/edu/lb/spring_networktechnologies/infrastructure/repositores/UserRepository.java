package edu.lb.spring_networktechnologies.infrastructure.repositores;

import edu.lb.spring_networktechnologies.infrastructure.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    // select user by email
    @Query("SELECT u FROM UserEntity u WHERE u.email = ?1")
    Collection<UserEntity> findByEmail(String email);

    // exists by email
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.email = ?1")
    boolean existsByEmail(String email);
}
