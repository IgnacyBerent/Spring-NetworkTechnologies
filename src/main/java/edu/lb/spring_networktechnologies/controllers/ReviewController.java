package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.exceptions.CheckBindingExceptions;
import edu.lb.spring_networktechnologies.infrastructure.dtos.review.CreateReviewDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.review.CreateReviewResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.review.GetReviewDto;
import edu.lb.spring_networktechnologies.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@PreAuthorize("isAuthenticated()")
public class ReviewController {
    public final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * Get all reviews from the database using pagination
     * @param page - page number
     * @param size - number of reviews per page
     * @return List of GetReviewDto objects containing information about the reviews
     */
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<GetReviewDto>> getAllBooks(@PathVariable Long bookId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(reviewService.getAllBookReviews(bookId, page, size), HttpStatus.OK);
    }

    /**
     * Create a new review
     * @param review - CreateReviewDto object containing information about the review
     * @param bindingResult - BindingResult object containing information about the validation
     * @return CreateReviewResponseDto object containing information about the review
     * @throws ResponseStatusException - if there are any validation errors
     */
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED) //code 201
    public ResponseEntity<CreateReviewResponseDto> create(@Valid @RequestBody CreateReviewDto review, BindingResult bindingResult) {
        CheckBindingExceptions.check(bindingResult);
        var newReview = reviewService.create(review);
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }

    /**
     * Get a single review by its id
     * @param id - id of the review
     * @return GetReviewDto object containing information about the review
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<GetReviewDto> getLoan(@PathVariable Long id) {
        return new ResponseEntity<>(reviewService.getOne(id), HttpStatus.OK);
    }

    /**
     * Delete a review by its id
     * @param id - id of the review
     * @return ResponseEntity object with no content
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
