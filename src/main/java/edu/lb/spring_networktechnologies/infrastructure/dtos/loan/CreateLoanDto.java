package edu.lb.spring_networktechnologies.infrastructure.dtos.loan;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CreateLoanDto {
    @NotNull(message = "User ID is required")
    @Schema(description = "ID of the user", example = "1")
    private Long userId;
    @NotNull(message = "Book ID is required")
    @Schema(description = "ID of the book", example = "1")
    private Long bookId;
    @NotNull(message = "Due date is required")
    @Schema(description = "Due date of the loan", example = "2021-12-31")
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
