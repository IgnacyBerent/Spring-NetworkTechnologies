package edu.lb.spring_networktechnologies.infrastructure.dtos.review;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public class CreateReviewDto {
    @NotEmpty(message = "Book ID is required")
    private Long bookId;
    @NotEmpty(message = "User ID is required")
    private Long userId;
    @NotEmpty(message = "Rating is required")
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
