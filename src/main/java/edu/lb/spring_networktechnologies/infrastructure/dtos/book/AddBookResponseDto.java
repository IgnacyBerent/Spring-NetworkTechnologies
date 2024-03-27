package edu.lb.spring_networktechnologies.infrastructure.dtos.book;

public class AddBookResponseDto {
    private Long id;
    private int copies;

    public AddBookResponseDto(Long id, int copies) {
        this.id = id;
        this.copies = copies;
    }

    public AddBookResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }
}
