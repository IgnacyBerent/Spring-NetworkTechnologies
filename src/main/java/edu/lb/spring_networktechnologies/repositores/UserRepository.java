package edu.lb.spring_networktechnologies.repositores;

import edu.lb.spring_networktechnologies.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
