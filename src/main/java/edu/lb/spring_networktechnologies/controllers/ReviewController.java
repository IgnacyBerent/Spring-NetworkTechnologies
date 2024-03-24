package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.infrastructure.dtos.review.CreateReviewDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.review.CreateReviewResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.review.GetReviewDto;
import edu.lb.spring_networktechnologies.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    public final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/getAll")
    public @ResponseBody List<GetReviewDto> getAllBooks() {
        return reviewService.getAll();
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED) //code 201
    public ResponseEntity<CreateReviewResponseDto> create(@RequestBody CreateReviewDto review) {
        var newReview = reviewService.create(review);
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public GetReviewDto getLoan(@PathVariable Long id) {
        return reviewService.getOne(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
