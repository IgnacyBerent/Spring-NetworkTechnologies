package edu.lb.spring_networktechnologies.infrastructure.dtos.loan;

import edu.lb.spring_networktechnologies.infrastructure.dtos.book.GetBookDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.GetUserDto;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

public class GetLoanDto {
    private Long id;
    private LocalDate dateOfLoan;
    private LocalDate dueDate;
    @Nullable
    private LocalDate returnDate;
    private GetUserDto user;
    private GetBookDto book;


    public GetLoanDto(Long id, LocalDate dateOfLoan, LocalDate dueDate, @Nullable LocalDate returnDate, GetUserDto user, GetBookDto book) {
        this.id = id;
        this.dateOfLoan = dateOfLoan;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
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

    @Nullable
    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(@Nullable LocalDate returnDate) {
        this.returnDate = returnDate;
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
