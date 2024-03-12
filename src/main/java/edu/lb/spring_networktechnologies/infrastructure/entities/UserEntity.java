package edu.lb.spring_networktechnologies.infrastructure.entities;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "users", schema = "library")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "loans")
    private List<LoanEntity> loans;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "reviews")
    private List<ReviewEntity> reviews;

    public Long getId() {
        return id;
    }

    public void setId(Long userId) {
        this.id = userId;
    }

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

    public List<LoanEntity> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanEntity> loans) {
        this.loans = loans;
    }

    public void addLoan(LoanEntity loan) {
        this.loans.add(loan);
    }

    public void removeLoan(LoanEntity loan) {
        this.loans.remove(loan);
    }

    public void clearLoans() {
        this.loans.clear();
    }

    public boolean hasLoans() {
        return !this.loans.isEmpty();
    }

    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    public void addReview(ReviewEntity review) {
        this.reviews.add(review);
    }

    public void removeReview(ReviewEntity review) {
        this.reviews.remove(review);
    }
}
