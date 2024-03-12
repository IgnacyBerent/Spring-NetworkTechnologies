package edu.lb.spring_networktechnologies.infrastructure.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "books", schema = "library")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "isbn", unique = true)
    private String isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publication_year")
    private int publicationYear;

    @Column(name = "available_copies")
    private int availableCopies;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "loaned")
    private List<Loan> loaned;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "reviews")
    private List<Review> reviews;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public List<Loan> getLoaned() {
        return loaned;
    }

    public void setLoaned(List<Loan> loaned) {
        this.loaned = loaned;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
