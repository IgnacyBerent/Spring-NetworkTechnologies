package edu.lb.spring_networktechnologies.infrastructure.dtos.loan;

import java.time.LocalDate;

public class GetLoanDto {
    private Long id;
    private String bookTitle;
    private String userName;
    private LocalDate dateOfLoan;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public GetLoanDto(Long id, String bookTitle, String userName, LocalDate dateOfLoan, LocalDate dueDate, LocalDate returnDate) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.userName = userName;
        this.dateOfLoan = dateOfLoan;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public GetLoanDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDate getDateOfLoan() {
        return dateOfLoan;
    }

    public void setDateOfLoan(LocalDate dateOfLoan) {
        this.dateOfLoan = dateOfLoan;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
