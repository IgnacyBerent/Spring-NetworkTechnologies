package edu.lb.spring_networktechnologies.infrastructure.dtos.auth;

import edu.lb.spring_networktechnologies.commonTypes.UserRole;

public class RegisterDto {
    private String password;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
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
