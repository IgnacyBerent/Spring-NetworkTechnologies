package edu.lb.spring_networktechnologies.infrastructure.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "review", schema = "library")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @Column(name = "book")
    private Book book;

    @ManyToOne
    @Column(name = "user")
    private User user;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "review_date")
    private LocalDate reviewDate;

    public Long getReviewId() {
        return id;
    }

    public void setReviewId(Long reviewId) {
        this.id = reviewId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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
