package edu.lb.spring_networktechnologies.infrastructure.dtos.review;

import edu.lb.spring_networktechnologies.infrastructure.dtos.book.GetBookDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.GetUserDto;

import java.time.LocalDate;

public class GetReviewDto {
    private Long id;
    private GetBookDto book;
    private GetUserDto user;
    private Float rating;
    private String comment;
    private LocalDate reviewDate;

    public GetReviewDto(Long id, GetBookDto book, GetUserDto user, Float rating, String comment, LocalDate reviewDate) {
        this.id = id;
        this.book = book;
        this.user = user;
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

    public GetBookDto getBook() {
        return book;
    }

    public void setBook(GetBookDto book) {
        this.book = book;
    }

    public GetUserDto getUser() {
        return user;
    }

    public void setUser(GetUserDto user) {
        this.user = user;
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
