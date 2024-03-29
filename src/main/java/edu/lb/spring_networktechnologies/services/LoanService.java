package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.exceptions.NotFoundException;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.GetBookDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.CreateLoanDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.CreateLoanResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.GetLoanDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.GetUserDto;
import edu.lb.spring_networktechnologies.infrastructure.entities.BookEntity;
import edu.lb.spring_networktechnologies.infrastructure.entities.LoanEntity;
import edu.lb.spring_networktechnologies.infrastructure.entities.UserEntity;
import edu.lb.spring_networktechnologies.infrastructure.repositores.BookRepository;
import edu.lb.spring_networktechnologies.infrastructure.repositores.LoanRepository;
import edu.lb.spring_networktechnologies.infrastructure.repositores.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<GetLoanDto> getAll() {
        var loans = loanRepository.findAll();

        return StreamSupport.stream(loans.spliterator(), false)
                .map(loanEntity -> new GetLoanDto(
                        loanEntity.getId(),
                        loanEntity.getLoanDate(),
                        loanEntity.getDueDate(),
                        mapUser(loanEntity.getUser()),
                        mapBook(loanEntity.getBook())
                )).collect(Collectors.toList());
    }

    public GetLoanDto getOne(Long id) {
        var loanEntity = loanRepository.findById(id).orElseThrow(NotFoundException::loan);

        return new GetLoanDto(
                loanEntity.getId(),
                loanEntity.getLoanDate(),
                loanEntity.getDueDate(),
                mapUser(loanEntity.getUser()),
                mapBook(loanEntity.getBook())
        );
    }

    public CreateLoanResponseDto create(CreateLoanDto loan) {
        UserEntity user = userRepository.findById(loan.getUserId()).orElseThrow(NotFoundException::user);
        BookEntity book = bookRepository.findById(loan.getBookId()).orElseThrow(NotFoundException::book);
        LocalDate currentDate = LocalDate.now();
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setUser(user);
        loanEntity.setBook(book);
        loanEntity.setLoanDate(currentDate);
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

    private GetUserDto mapUser(UserEntity user) {
        return new GetUserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    private GetBookDto mapBook(BookEntity book) {
        return new GetBookDto(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getPublicationYear(),
                book.getAvailableCopies() > 0
        );
    }

}
