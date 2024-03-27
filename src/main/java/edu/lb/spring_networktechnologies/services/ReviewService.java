package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.exceptions.NotFoundException;
import edu.lb.spring_networktechnologies.infrastructure.dtos.review.CreateReviewDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.review.CreateReviewResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.review.GetReviewDto;
import edu.lb.spring_networktechnologies.infrastructure.entities.BookEntity;
import edu.lb.spring_networktechnologies.infrastructure.entities.ReviewEntity;
import edu.lb.spring_networktechnologies.infrastructure.entities.UserEntity;
import edu.lb.spring_networktechnologies.infrastructure.repositores.BookRepository;
import edu.lb.spring_networktechnologies.infrastructure.repositores.ReviewRepository;
import edu.lb.spring_networktechnologies.infrastructure.repositores.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    @Autowired
    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<GetReviewDto> getAll() {
        var reviews = reviewRepository.findAll();

        return StreamSupport.stream(reviews.spliterator(), false)
                .map(review -> new GetReviewDto(
                        review.getId(),
                        review.getBook().getTitle(),
                        review.getUser().getFirstName(),
                        review.getRating(),
                        review.getComment(),
                        review.getReviewDate()
                )).collect(Collectors.toList());
    }

    public GetReviewDto getOne(Long id) {
        var reviewEntity = reviewRepository.findById(id).orElseThrow(NotFoundException::review);

        return new GetReviewDto(
                reviewEntity.getId(),
                reviewEntity.getBook().getTitle(),
                reviewEntity.getUser().getFirstName(),
                reviewEntity.getRating(),
                reviewEntity.getComment(),
                reviewEntity.getReviewDate()
        );
    }

    public CreateReviewResponseDto create(CreateReviewDto review) {
        var reviewEntity = new ReviewEntity();
        BookEntity book = bookRepository.findById(review.getBookId()).orElseThrow(() -> new RuntimeException("Book not found"));
        UserEntity user = userRepository.findById(review.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        LocalDate currentDate = LocalDate.now();


        reviewEntity.setBook(book);
        reviewEntity.setUser(user);
        reviewEntity.setRating(review.getRating());
        reviewEntity.setComment(review.getComment());
        reviewEntity.setReviewDate(currentDate);

        var newReview = reviewRepository.save(reviewEntity);

        return new CreateReviewResponseDto(
                newReview.getId(),
                newReview.getBook().getId(),
                newReview.getUser().getId(),
                newReview.getRating(),
                newReview.getComment(),
                newReview.getReviewDate()
        );
    }

    public void delete(Long id) {
        if(!reviewRepository.existsById(id)) {
            log.info("Review with id: {} not found", id);
            throw NotFoundException.review();
        }
        reviewRepository.deleteById(id);
    }
}
