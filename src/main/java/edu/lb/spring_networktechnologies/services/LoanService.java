package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.LoanCreateDTO;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.LoanDTO;
import edu.lb.spring_networktechnologies.infrastructure.entities.Book;
import edu.lb.spring_networktechnologies.infrastructure.entities.Loan;
import edu.lb.spring_networktechnologies.infrastructure.entities.User;
import edu.lb.spring_networktechnologies.infrastructure.repositores.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private BookService bookService;
    private UserService userService;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan toLoan(LoanCreateDTO loanCreateDTO) {
        Loan loan = new Loan();
        Book book = bookService.getBookById(loanCreateDTO.getBookId());
        User user = userService.getUserById(loanCreateDTO.getUserId());
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(loanCreateDTO.getDateOfLoan());
        loan.setReturnDate(loanCreateDTO.getReturnedDate());
        return loan;
    }

    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public LoanDTO toLoanDTO(Loan loan) {
        LoanDTO loanDTO = new LoanDTO();
        Book book = loan.getBook();
        User user = loan.getUser();
        loanDTO.setBookTitle(book.getTitle());
        loanDTO.setUserName(user.getName());
        loanDTO.setDateOfLoan(loan.getLoanDate());
        loanDTO.setDueDate(loan.getReturnDate());
        loanDTO.setIsReturned(loan.getReturnDate() != null);
        return loanDTO;
    }

    public Iterable<LoanDTO> getAllLoans() {
        Iterable<Loan> loans = loanRepository.findAll();
        List<LoanDTO> loanDTOs = new ArrayList<>();
        for (Loan loan : loans) {
            loanDTOs.add(toLoanDTO(loan));
        }
        return loanDTOs;
    }
}
