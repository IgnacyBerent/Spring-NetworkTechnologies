package edu.lb.spring_networktechnologies.dtos.book;

public class BookDTO {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private Long year;
    private Long availableCopies;

    private Boolean isLoaned;

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
}
