package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.exceptions.NotFoundException;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.CreateLoanDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.CreateLoanResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.GetLoanDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.GetLoansPageDto;
import edu.lb.spring_networktechnologies.infrastructure.entities.BookEntity;
import edu.lb.spring_networktechnologies.infrastructure.entities.LoanEntity;
import edu.lb.spring_networktechnologies.infrastructure.entities.UserEntity;
import edu.lb.spring_networktechnologies.infrastructure.repositores.AuthRepository;
import edu.lb.spring_networktechnologies.infrastructure.repositores.BookRepository;
import edu.lb.spring_networktechnologies.infrastructure.repositores.LoanRepository;
import edu.lb.spring_networktechnologies.infrastructure.repositores.UserRepository;
import edu.lb.spring_networktechnologies.infrastructure.mappings.MapLoan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class LoanService extends OwnershipService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository, AuthRepository authRepository) {
        super(authRepository);
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @PreAuthorize("hasRole('ADMIN') or isAuthenticated() and this.isOwner(authentication.name, #userId)")
    public GetLoansPageDto getAll(Long userId, int page, int size) {
        Page<LoanEntity> loansPage;
        Pageable pageable = PageRequest.of(page, size);

        if (userId != null) {
            loansPage = loanRepository.findByUserId(userId, pageable);
        } else {
            loansPage = loanRepository.findAll(pageable);
        }

        List<GetLoanDto> loansDto = loansPage.getContent().stream()
                .map(MapLoan::toGetLoanDto).toList();

        return new GetLoansPageDto(
                loansDto,
                loansPage.getNumber(),
                loansPage.getTotalPages(),
                loansPage.getTotalElements(),
                loansPage.hasNext());
    }

    @PostAuthorize("hasRole('ADMIN') or isAuthenticated() and this.isOwner(authentication.name, returnObject.user.id)")
    public GetLoanDto getOne(Long id) {
        var loanEntity = loanRepository.findById(id).orElseThrow(NotFoundException::loan);

        return MapLoan.toGetLoanDto(loanEntity);
    }

    @PreAuthorize("hasRole('ADMIN') or isAuthenticated() and this.isOwner(authentication.name, #loan.userId)")
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

}
