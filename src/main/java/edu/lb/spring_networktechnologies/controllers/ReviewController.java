package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.exceptions.CheckBindingExceptions;
import edu.lb.spring_networktechnologies.infrastructure.dtos.review.CreateReviewDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.review.CreateReviewResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.review.GetReviewDto;
import edu.lb.spring_networktechnologies.services.ReviewService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Reviews", description = "Endpoints for reviews")
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
    @PreAuthorize("permitAll")
    @SecurityRequirements
    @ApiResponse(responseCode = "200", description = "Reviews found")
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
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
                    @ApiResponse(responseCode = "201", description = "Review created"),
            }
    )
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
    @PreAuthorize("permitAll")
    @SecurityRequirements
    @ApiResponse(responseCode = "200", description = "Review found")
    public ResponseEntity<GetReviewDto> getLoan(@PathVariable Long id) {
        return new ResponseEntity<>(reviewService.getOne(id), HttpStatus.OK);
    }

    /**
     * Delete a review by its id
     * @param id - id of the review
     * @return ResponseEntity object with no content
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Review deleted", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Review not found", content = @Content),
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
