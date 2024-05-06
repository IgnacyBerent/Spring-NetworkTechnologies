package edu.lb.spring_networktechnologies.infrastructure.dtos.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateBookDto {
    @NotEmpty(message = "book image is required")
    @Schema(description = "Image of the book", example = "https://example.com/image.jpg")
    private String img;
    @NotEmpty(message = "ISBN is required")
    @Schema(description = "ISBN of the book", example = "978-3-16-148410-0")
    private String isbn;
    @NotEmpty(message = "Title is required")
    @Schema(description = "Title of the book", example = "Book Title")
    private String title;
    @NotEmpty(message = "Author is required")
    @Schema(description = "Author of the book", example = "Author Name")
    private String author;
    @NotEmpty(message = "Publisher is required")
    @Schema(description = "Publisher of the book", example = "Publisher Name")
    private String publisher;
    @NotNull(message = "Publication year is required")
    @Min(value = 1000, message = "Publication year must be at least 1000")
    @Schema(description = "Publication year of the book", example = "2021")
    private int publicationYear;
    @NotNull(message = "Available copies is required")
    @Min(value = 0, message = "Available copies must be at least 0")
    @Schema(description = "Number of available copies of the book", example = "10")
    private int availableCopies;
    @NotEmpty(message = "Genre is required")
    @Schema(description = "Genre of the book", example = "Fantasy")
    private String genre;
    @NotEmpty(message = "Summary is required")
    @Size(max = 3000, message = "Summary length must be less than 3000 characters")
    @Schema(description = "Summary of the book", example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Velit euismod in pellentesque massa placerat. Vel pharetra vel turpis nunc eget lorem.")
    private String summary;

    public CreateBookDto() {
    }

    public CreateBookDto(String img, String isbn, String title, String author, String publisher, int publicationYear, int availableCopies, String genre, String summary) {
        this.img = img;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.availableCopies = availableCopies;
        this.genre = genre;
        this.summary = summary;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
