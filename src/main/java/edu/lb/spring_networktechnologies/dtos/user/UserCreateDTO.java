package edu.lb.spring_networktechnologies.dtos.user;

import java.util.List;

public class UserCreateDTO {
    private String username;
    private String password;
    private String role;
    private String email;
    private String name;
    private List<Long> loans;
    private List<Long> reviews;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getLoans() {
        return loans;
    }

    public void setLoans(List<Long> loans) {
        this.loans = loans;
    }

    public List<Long> getReviews() {
        return reviews;
    }

    public void setReviews(List<Long> reviewIds) {
        this.reviews = reviewIds;
    }
}