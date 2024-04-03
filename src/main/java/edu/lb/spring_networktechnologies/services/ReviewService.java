package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.exceptions.NotFoundException;
import edu.lb.spring_networktechnologies.infrastructure.dtos.review.CreateReviewDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.review.CreateReviewResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.review.GetReviewDto;
import edu.lb.spring_networktechnologies.infrastructure.entities.BookEntity;
import edu.lb.spring_networktechnologies.infrastructure.entities.ReviewEntity;
import edu.lb.spring_networktechnologies.infrastructure.entities.UserEntity;
import edu.lb.spring_networktechnologies.infrastructure.repositores.AuthRepository;
import edu.lb.spring_networktechnologies.infrastructure.repositores.BookRepository;
import edu.lb.spring_networktechnologies.infrastructure.repositores.ReviewRepository;
import edu.lb.spring_networktechnologies.infrastructure.repositores.UserRepository;
import edu.lb.spring_networktechnologies.infrastructure.mappings.MapReview;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Slf4j
public class ReviewService extends OwnershipService {

    private final ReviewRepository reviewRepository;

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    @Autowired
    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository, AuthRepository authRepository) {
        super(authRepository);
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    /**
     * Method for getting all reviews of a book from the database using pagination
     * @param bookId - id of the book whose reviews are to be fetched
     * @param page - page number
     * @param size - number of reviews per page
     * @return List of GetReviewDto objects containing information about the reviews
     */
    public List<GetReviewDto> getAllBookReviews(Long bookId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ReviewEntity> reviews = reviewRepository.findByBookId(bookId, pageable);

        return reviews
                .stream()
                .map(MapReview::toGetReviewDto)
                .collect(Collectors.toList());
    }

    /**
     * Method for getting a single review by its id
     * @param id - id of the review
     * @return GetReviewDto object containing information about the review
     * @throws NotFoundException - if review with given id does not exist
     */
    public GetReviewDto getOne(Long id) {
        var reviewEntity = reviewRepository.findById(id).orElseThrow(NotFoundException::review);

        return MapReview.toGetReviewDto(reviewEntity);
    }

    /**
     * Method for creating a new review
     * @param review - CreateReviewDto object containing information about the review
     * @return CreateReviewResponseDto object containing information about the created review
     */
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

    /**
     * Method for deleting a review by its id
     * @param id - id of the review
     * @throws NotFoundException - if review with given id does not exist
     */
    public void delete(Long id) {
        if(!reviewRepository.existsById(id)) {
            log.info("Review with id: {} not found", id);
            throw NotFoundException.review();
        }
        reviewRepository.deleteById(id);
    }
}
