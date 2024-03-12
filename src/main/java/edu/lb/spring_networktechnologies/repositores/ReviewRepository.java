package edu.lb.spring_networktechnologies.repositores;

import edu.lb.spring_networktechnologies.entities.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {
}
