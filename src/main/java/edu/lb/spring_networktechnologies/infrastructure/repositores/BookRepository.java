package edu.lb.spring_networktechnologies.infrastructure.repositores;

import edu.lb.spring_networktechnologies.infrastructure.entities.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
