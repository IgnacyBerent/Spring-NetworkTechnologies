package edu.lb.spring_networktechnologies.infrastructure.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public class LoginDto {
    @NotEmpty(message = "Username is required")
    @Schema(description = "Username of the user", example = "user")
    private String username;
    @NotEmpty(message = "Password is required")
    @Schema(description = "Password of the user", example = "password")
    private String password;

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
