package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.infrastructure.dtos.book.CreateBookDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.CreateBookResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.GetBookDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.CreateLoanDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.CreateLoanResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.GetLoanDto;
import edu.lb.spring_networktechnologies.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/getAll")
    public @ResponseBody List<GetLoanDto> getAllBooks() {
        return loanService.getAll();
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED) //code 201
    public ResponseEntity<CreateLoanResponseDto> create(@RequestBody CreateLoanDto book) {
        var newLoan = loanService.create(book);
        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public GetLoanDto getLoan(@PathVariable Long id) {
        return loanService.getOne(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
