package edu.lb.spring_networktechnologies.infrastructure.repositores;

import edu.lb.spring_networktechnologies.infrastructure.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
