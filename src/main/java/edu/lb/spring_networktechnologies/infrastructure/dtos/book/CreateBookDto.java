package edu.lb.spring_networktechnologies.infrastructure.dtos.book;

import jakarta.validation.constraints.NotEmpty;

public class CreateBookDto {
    @NotEmpty(message = "ISBN is required")
    private String isbn;
    @NotEmpty(message = "Title is required")
    private String title;
    @NotEmpty(message = "Author is required")
    private String author;
    @NotEmpty(message = "Publisher is required")
    private String publisher;
    @NotEmpty(message = "Publication year is required")
    private int publicationYear;
    @NotEmpty(message = "Available copies is required")
    private int availableCopies;

    public CreateBookDto(String isbn, String title, String author, String publisher, int publicationYear, int availableCopies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.availableCopies = availableCopies;
    }

    public CreateBookDto() {
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
}
