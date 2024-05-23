package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.exceptions.CheckBindingExceptions;
import edu.lb.spring_networktechnologies.exceptions.LoanAlreadyReturnedException;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.CreateLoanDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.CreateLoanResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.GetLoanDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.GetLoansPageDto;
import edu.lb.spring_networktechnologies.services.LoanService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/loans")
@PostAuthorize("isAuthenticated()")
@Tag(name = "Loans", description = "Endpoints for loans")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    /**
     * Get all loans from the database using pagination
     *
     * @param userId - id of the user whose loans are to be fetched
     * @param page   - page number
     * @param size   - number of loans per page
     * @return GetLoansPageDto object containing list of GetLoanDto objects and pagination information
     */
    @GetMapping("/getAll")
    @ApiResponse(responseCode = "200", description = "Loans found")
    public ResponseEntity<GetLoansPageDto> getAll(@RequestParam(required = false) Long userId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        GetLoansPageDto loans = loanService.getAll(userId, page, size);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    /**
     * Create a new loan
     *
     * @param loan          - CreateLoanDto object containing information about the loan
     * @param bindingResult - BindingResult object containing information about the validation
     * @return CreateLoanResponseDto object containing information about the loan
     * @throws ResponseStatusException - if there are any validation errors
     * @throws EntityNotFoundException - if the user or book does not exist
     */
    @PostMapping("/add")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "400", description = "Validation error"),
                    @ApiResponse(responseCode = "404", description = "User or book not found"),
                    @ApiResponse(responseCode = "201", description = "Loan created", content = @Content),
            }
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<CreateLoanResponseDto> create(@Valid @RequestBody CreateLoanDto loan, BindingResult bindingResult) {
        CheckBindingExceptions.check(bindingResult);
        var newLoan = loanService.create(loan);
        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }

    /**
     * Return a loan
     *
     * @param id - id of the loan
     * @throws EntityNotFoundException      - if the loan or borrowed does not exist
     * @throws LoanAlreadyReturnedException - if the loan is already returned
     */
    @PutMapping("/return/{id}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Loan returned", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Loan or Book not found", content = @Content),
                    @ApiResponse(responseCode = "409", description = "Loan already returned", content = @Content),
            }
    )
    public ResponseEntity<Void> returnLoan(@PathVariable Long id) {
        loanService.returnLoan(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get a single loan by its id
     *
     * @param id - id of the loan
     * @return GetLoanDto object containing information about the loan
     * @throws EntityNotFoundException - if the loan does not exist
     */
    @GetMapping("/get/{id}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Loan found", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Loan not found", content = @Content),
            }
    )
    public GetLoanDto getLoan(@PathVariable Long id) {
        return loanService.getOne(id);
    }

    /**
     * Extend the loan
     *
     * @param id - id of the loan
     * @param days - number of days to extend the loan
     * @throws EntityNotFoundException - if the loan does not exist
     * @throws LoanAlreadyReturnedException - if the loan is already returned
     */
    @PutMapping("/extend/{id}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Loan extended", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Loan not found", content = @Content),
                    @ApiResponse(responseCode = "409", description = "Loan already returned", content = @Content),
            }
    )
    public ResponseEntity<Void> extendLoan(@PathVariable Long id, @RequestParam int days) {
        loanService.extendLoan(id, days);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete a loan by its id
     *
     * @param id - id of the loan
     * @throws EntityNotFoundException - if the loan does not exist
     */
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Loan deleted", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Loan not found", content = @Content),
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
