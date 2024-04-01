package edu.lb.spring_networktechnologies.infrastructure.repositores;

import edu.lb.spring_networktechnologies.infrastructure.entities.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByUserId(Long userId);
    Page<ReviewEntity> findByUserId(Long userId, Pageable pageable);
    List<ReviewEntity> findByBookId(Long bookId);
    Page<ReviewEntity> findByBookId(Long bookId, Pageable pageable);
}
