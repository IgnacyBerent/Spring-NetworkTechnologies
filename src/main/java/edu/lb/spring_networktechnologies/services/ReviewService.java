package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.infrastructure.dtos.review.ReviewCreateDTO;
import edu.lb.spring_networktechnologies.infrastructure.dtos.review.ReviewDTO;
import edu.lb.spring_networktechnologies.infrastructure.entities.Book;
import edu.lb.spring_networktechnologies.infrastructure.entities.Review;
import edu.lb.spring_networktechnologies.infrastructure.entities.User;
import edu.lb.spring_networktechnologies.infrastructure.repositores.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private BookService bookService;
    private UserService userService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review toLoan(ReviewCreateDTO reviewCreateDTO) {
        Review review = new Review();
        Book book = bookService.getBookById(reviewCreateDTO.getBookId());
        User user = userService.getUserById(reviewCreateDTO.getUserId());
        review.setBook(book);
        review.setUser(user);
        review.setRating(reviewCreateDTO.getRating());
        review.setComment(reviewCreateDTO.getComment());
        review.setReviewDate(reviewCreateDTO.getReviewDate());
        return review;
    }

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public ReviewDTO toReviewDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        Book book = review.getBook();
        User user = review.getUser();
        reviewDTO.setBookId(book.getBookId());
        reviewDTO.setUserId(user.getUserId());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setComment(review.getComment());
        reviewDTO.setReviewDate(review.getReviewDate());
        return reviewDTO;
    }

    public Iterable<ReviewDTO> getAllReviews() {
        Iterable<Review> reviews = reviewRepository.findAll();
        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        for (Review review : reviews) {
            reviewDTOs.add(toReviewDTO(review));
        }
        return reviewDTOs;
    }
}
