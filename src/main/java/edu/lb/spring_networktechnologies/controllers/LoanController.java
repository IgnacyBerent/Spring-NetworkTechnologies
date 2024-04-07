package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.exceptions.CheckBindingExceptions;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.CreateLoanDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.CreateLoanResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.GetLoanDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.GetLoansPageDto;
import edu.lb.spring_networktechnologies.services.LoanService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
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
     * @param userId - id of the user whose loans are to be fetched
     * @param page - page number
     * @param size - number of loans per page
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
     * @param loan - CreateLoanDto object containing information about the loan
     * @param bindingResult - BindingResult object containing information about the validation
     * @return CreateLoanResponseDto object containing information about the loan
     */
    @PostMapping("/add")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
                    @ApiResponse(responseCode = "201", description = "Loan created"),
            }
    )
    @ResponseStatus(code = HttpStatus.CREATED) //code 201
    public ResponseEntity<CreateLoanResponseDto> create(@Valid @RequestBody CreateLoanDto loan, BindingResult bindingResult) {
        CheckBindingExceptions.check(bindingResult);
        var newLoan = loanService.create(loan);
        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }

    /**
     * Get a single loan by its id
     * @param id - id of the loan
     * @return GetLoanDto object containing information about the loan
     */
    @GetMapping("/get/{id}")
    @ApiResponse(responseCode = "200", description = "Loan found")
    public GetLoanDto getLoan(@PathVariable Long id) {
        return loanService.getOne(id);
    }

    /**
     * Delete a loan by its id
     * @param id - id of the loan
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
