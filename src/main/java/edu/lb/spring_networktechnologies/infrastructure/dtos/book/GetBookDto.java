package edu.lb.spring_networktechnologies.infrastructure.dtos.book;

public class GetBookDto {
    private Long id;
    private String img;
    private String title;
    private String author;
    private float rating;
    private boolean isAvailable;

    public GetBookDto() {
    }

    public GetBookDto(Long id, String img, String title, String author, float rating, boolean isAvailable) {
        this.id = id;
        this.img = img;
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.isAvailable = isAvailable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}