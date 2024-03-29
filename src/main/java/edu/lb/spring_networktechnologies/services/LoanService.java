package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.exceptions.NotFoundException;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.CreateLoanDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.CreateLoanResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.GetLoanDto;
import edu.lb.spring_networktechnologies.infrastructure.entities.BookEntity;
import edu.lb.spring_networktechnologies.infrastructure.entities.LoanEntity;
import edu.lb.spring_networktechnologies.infrastructure.entities.UserEntity;
import edu.lb.spring_networktechnologies.infrastructure.repositores.BookRepository;
import edu.lb.spring_networktechnologies.infrastructure.repositores.LoanRepository;
import edu.lb.spring_networktechnologies.infrastructure.repositores.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    @Autowired
    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<GetLoanDto> getAll() {
        var loans = loanRepository.findAll();

        return StreamSupport.stream(loans.spliterator(), false)
                .map(loan -> new GetLoanDto(
                        loan.getId(),
                        loan.getBook().getTitle(),
                        loan.getUser().getFirstName(),
                        loan.getLoanDate(),
                        loan.getDueDate(),
                        loan.getReturnDate()
                )).collect(Collectors.toList());
    }

    public GetLoanDto getOne(Long id) {
        var loanEntity = loanRepository.findById(id).orElseThrow(NotFoundException::loan);

        return new GetLoanDto(
                loanEntity.getId(),
                loanEntity.getBook().getTitle(),
                loanEntity.getUser().getFirstName(),
                loanEntity.getLoanDate(),
                loanEntity.getDueDate(),
                loanEntity.getReturnDate()
        );
    }

    public CreateLoanResponseDto create(CreateLoanDto loan) {
        var loanEntity = new LoanEntity();
        UserEntity user = userRepository.findById(loan.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        BookEntity book = bookRepository.findById(loan.getBookId()).orElseThrow(() -> new RuntimeException("Book not found"));
        loanEntity.setUser(user);
        loanEntity.setBook(book);
        loanEntity.setLoanDate(loan.getLoanDate());
        loanEntity.setDueDate(loan.getDueDate());
        var newLoan = loanRepository.save(loanEntity);

        return new CreateLoanResponseDto(
                newLoan.getId(),
                newLoan.getUser().getId(),
                newLoan.getBook().getId(),
                newLoan.getLoanDate(),
                newLoan.getDueDate()
        );
    }

    public void delete(Long id) {
        if(!loanRepository.existsById(id)) {
            log.info("Loan with id: {} not found", id);
            throw NotFoundException.loan();
        }
        loanRepository.deleteById(id);
    }
}
