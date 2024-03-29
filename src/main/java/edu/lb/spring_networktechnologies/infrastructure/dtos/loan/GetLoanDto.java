package edu.lb.spring_networktechnologies.infrastructure.dtos.loan;

import edu.lb.spring_networktechnologies.infrastructure.dtos.book.GetBookDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.GetUserDto;

import java.time.LocalDate;

public class GetLoanDto {
    private Long id;
    private LocalDate dateOfLoan;
    private LocalDate dueDate;
    private GetUserDto user;
    private GetBookDto book;

    public GetLoanDto(Long id, LocalDate dateOfLoan, LocalDate dueDate, GetUserDto user, GetBookDto book) {
        this.id = id;
        this.dateOfLoan = dateOfLoan;
        this.dueDate = dueDate;
        this.user = user;
        this.book = book;
    }

    public GetLoanDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public GetUserDto getUser() {
        return user;
    }

    public void setUser(GetUserDto user) {
        this.user = user;
    }

    public GetBookDto getBook() {
        return book;
    }

    public void setBook(GetBookDto book) {
        this.book = book;
    }
}
