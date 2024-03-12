package edu.lb.spring_networktechnologies.infrastructure.dtos.user;

public class GetUserDto {
    private Long id;
    private String username;

    public GetUserDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public GetUserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}


