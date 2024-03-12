package edu.lb.spring_networktechnologies.infrastructure.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "loan", schema = "library")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @Column(name = "user")
    private User user;

    @ManyToOne
    @Column(name = "book")
    private Book book;

    @Column(name = "loan_date")
    private LocalDate LoanDate;

    @Column(name = "due_date")
    private LocalDate DueDate;

    @Column(name = "return_date")
    private LocalDate ReturnDate;

    public Long getLoanId() {
        return id;
    }

    public void setLoanId(Long loanId) {
        this.id = loanId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getLoanDate() {
        return LoanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        LoanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return DueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        DueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        ReturnDate = returnDate;
    }
}
