package edu.lb.spring_networktechnologies.infrastructure.repositores;

import edu.lb.spring_networktechnologies.infrastructure.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    boolean existsByIsbn(String isbn);

    @Query("SELECT b FROM BookEntity b WHERE lower(b.title) LIKE lower(concat('%', :searchTerm, '%'))")
    Page<BookEntity> findAllByTitle(String searchTerm, Pageable pageable);
}
