package edu.lb.spring_networktechnologies.infrastructure.dtos.review;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class CreateReviewDto {
    @NotNull(message = "Book ID is required")
    private Long bookId;
    @NotNull(message = "User ID is required")
    private Long userId;
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    @Digits(integer = 1, fraction = 1, message = "Rating must have one digit after the decimal point")
    private Float rating;
    @NotEmpty(message = "Comment is required")
    private String comment;

    public CreateReviewDto(Long bookId, Long userId, Float rating, String comment, LocalDate reviewDate) {
        this.bookId = bookId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }

    public CreateReviewDto() {
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
