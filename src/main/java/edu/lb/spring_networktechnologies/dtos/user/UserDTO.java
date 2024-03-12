package edu.lb.spring_networktechnologies.dtos.user;

import java.util.List;

public class UserDTO {
    private String username;
    private List<Long> loans;
    private List<Long> reviews;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void addReview(Long reviewId) {
        this.reviews.add(reviewId);
    }
    public void removeReview(Long reviewId) {
        this.reviews.remove(reviewId);
    }
}


