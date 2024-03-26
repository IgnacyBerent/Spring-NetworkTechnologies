package edu.lb.spring_networktechnologies.infrastructure.dtos.user;

public class DeleteUserDto {
    private Long id;

    public DeleteUserDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
