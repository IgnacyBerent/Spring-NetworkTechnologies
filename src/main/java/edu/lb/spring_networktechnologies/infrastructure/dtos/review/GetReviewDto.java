package edu.lb.spring_networktechnologies.infrastructure.dtos.review;

import java.time.LocalDate;

public class GetReviewDto {
    private Long id;
    private String bookTitle;
    private String userName;
    private Float rating;
    private String comment;
    private LocalDate reviewDate;

    public GetReviewDto(Long id, String bookTitle, String userName, Float rating, String comment, LocalDate reviewDate) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public GetReviewDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
}
