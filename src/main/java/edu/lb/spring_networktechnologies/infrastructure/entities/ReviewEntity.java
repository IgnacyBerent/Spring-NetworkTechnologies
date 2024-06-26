package edu.lb.spring_networktechnologies.infrastructure.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "review", schema = "library")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "rating", nullable = false)
    @Basic
    private Float rating;

    @Column(name = "comment", columnDefinition = "VARCHAR(3000)")
    @Basic
    private String comment;

    @Column(name = "review_date", nullable = false)
    @Basic
    private LocalDate reviewDate;

    public Long getId() {
        return id;
    }

    public void setId(Long reviewId) {
        this.id = reviewId;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
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
