package edu.lb.spring_networktechnologies.dtos.book;

import edu.lb.spring_networktechnologies.entities.Review;

import java.util.List;

public class BookDTO {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private Long year;
    private Long availableCopies;

    private Boolean isLoaned;
    private List<Long> reviewIds;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Long availableCopies) {
        this.availableCopies = availableCopies;
    }

    public Boolean isLoaned() {
        return isLoaned;
    }

    public void setLoaned(Boolean isLoaned) {
        this.isLoaned = isLoaned;
    }

    public List<Long> getReviewIds() {
        return reviewIds;
    }

    public void setReviewIds(List<Review> reviews) {
        for (Review review : reviews) {
            this.reviewIds.add(review.getReviewId());
        }
    }

    public void addReviewId(Long reviewId) {
        this.reviewIds.add(reviewId);
    }

    public void removeReviewId(Long reviewId) {
        this.reviewIds.remove(reviewId);
    }


}
