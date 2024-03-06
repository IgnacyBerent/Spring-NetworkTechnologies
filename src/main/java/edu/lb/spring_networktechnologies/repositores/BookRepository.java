package edu.lb.spring_networktechnologies.repositores;

import edu.lb.spring_networktechnologies.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
}
