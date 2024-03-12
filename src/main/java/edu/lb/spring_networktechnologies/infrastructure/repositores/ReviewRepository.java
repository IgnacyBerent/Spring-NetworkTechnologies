package edu.lb.spring_networktechnologies.infrastructure.repositores;

import edu.lb.spring_networktechnologies.infrastructure.entities.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
}
