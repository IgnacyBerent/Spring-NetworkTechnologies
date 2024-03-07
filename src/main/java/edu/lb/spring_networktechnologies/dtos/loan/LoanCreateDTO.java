package edu.lb.spring_networktechnologies.dtos.loan;

import java.time.LocalDate;

public class LoanCreateDTO {
    private Integer bookId;
    private Integer userId;
    private LocalDate dateOfLoan;
    private LocalDate dueDate;
    private LocalDate returnedDate;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }
}
