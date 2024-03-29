package edu.lb.spring_networktechnologies.infrastructure.dtos.loan;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CreateLoanDto {
    @NotNull(message = "User ID is required")
    private Long userId;
    @NotNull(message = "Book ID is required")
    private Long bookId;
    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    public CreateLoanDto(Long userId, Long bookId, LocalDate loanDate, LocalDate dueDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.dueDate = dueDate;
    }

    public CreateLoanDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
