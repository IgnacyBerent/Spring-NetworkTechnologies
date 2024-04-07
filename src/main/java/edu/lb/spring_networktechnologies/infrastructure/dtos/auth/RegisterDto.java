package edu.lb.spring_networktechnologies.infrastructure.dtos.auth;

import edu.lb.spring_networktechnologies.commonTypes.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RegisterDto {

    @NotEmpty(message = "Password is required")
    @Schema(description = "Password of the user", example = "password")
    private String password;
    @NotEmpty(message = "Username is required")
    @Schema(description = "Username of the user", example = "user")
    private String username;
    @NotEmpty(message = "First name is required")
    @Schema(description = "First name of the user", example = "John")
    private String firstName;
    @NotEmpty(message = "Last name is required")
    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;
    @Email(message = "Invalid email")
    @NotEmpty(message = "Email is required")
    @Schema(description = "Email of the user", example = "example@ex.com")
    private String email;
    @NotNull(message = "Role is required")
    @Schema(description = "Role of the user", example = "ROLE_USER")
    private UserRole role;

    public RegisterDto(String password, String username, String firstName, String lastName, String email, UserRole role) {
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public RegisterDto() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
