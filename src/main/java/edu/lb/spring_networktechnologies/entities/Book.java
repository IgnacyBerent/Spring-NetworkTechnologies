package edu.lb.spring_networktechnologies.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    private String isbn;

    private String title;

    private String author;

    private String publisher;

    private Long year;

    private Long availableCopies;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Loan> loaned;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Review> reviews;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

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

    public List<Loan> getLoaned() {
        return loaned;
    }

    public void setLoaned(List<Loan> loaned) {
        this.loaned = loaned;
    }

    public void addLoan(Loan loan) {
        this.loaned.add(loan);
    }

    public void removeLoan(Loan loan) {
        this.loaned.remove(loan);
    }

    public Boolean isCurrentlyLoaned() {
        // if any loan has null returnDate then it is currently loaned
        for (Loan loan : loaned) {
            if (loan.getReturnDate() == null) {
                return true;
            }
        }
        return false;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void removeReview(Review review) {
        this.reviews.remove(review);
    }

    public Double getAverageRating() {
        if (reviews.size() == 0) {
            return 0.0;
        }
        Double sum = 0.0;
        for (Review review : reviews) {
            sum += review.getRating();
        }
        return sum / reviews.size();
    }
}
