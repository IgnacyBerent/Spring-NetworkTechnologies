package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.exceptions.LoanAlreadyReturnedException;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.CreateLoanDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.CreateLoanResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.GetLoanDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.GetLoansPageDto;
import edu.lb.spring_networktechnologies.infrastructure.entities.BookEntity;
import edu.lb.spring_networktechnologies.infrastructure.entities.LoanEntity;
import edu.lb.spring_networktechnologies.infrastructure.entities.UserEntity;
import edu.lb.spring_networktechnologies.infrastructure.mappings.MapLoan;
import edu.lb.spring_networktechnologies.infrastructure.repositores.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class LoanService extends OwnershipService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository, AuthRepository authRepository, ReviewRepository reviewRepository) {
        super(authRepository);
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    /**
     * Method for getting all loans from the database using pagination in descending order by loan date.
     * it returns all loans in database if admin or only the loans of the user
     *
     * @param userId - id of the user whose loans are to be fetched
     * @param page   - page number
     * @param size   - number of loans per page
     * @return GetLoansPageDto object containing list of GetLoanDto objects and pagination information
     */
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
                .map(loanEntity -> {
                    Double averageRating = reviewRepository.calculateAverageRating(loanEntity.getBook().getId());
                    float avgRating = (averageRating != null) ? averageRating.floatValue() : 0.0f;
                    return MapLoan.toGetLoanDto(loanEntity, avgRating);
                }).toList();

        return new GetLoansPageDto(
                loansDto,
                loansPage.getNumber(),
                loansPage.getTotalPages(),
                loansPage.getTotalElements(),
                loansPage.hasNext());
    }

    /**
     * Method for getting a loan information by its id if is admin or the user is the owner of the loan
     *
     * @param id - id of the loan
     * @return GetLoanDto object containing information about the loan
     * @throws EntityNotFoundException - if loan with given id does not exist
     */
    @PostAuthorize("hasRole('ADMIN') or isAuthenticated() and this.isOwner(authentication.name, returnObject.user.id)")
    public GetLoanDto getOne(Long id) {
        var loanEntity = loanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Loan with id: " + id + " not found"));

        Double averageRating = reviewRepository.calculateAverageRating(loanEntity.getBook().getId());
        float avgRating = (averageRating != null) ? averageRating.floatValue() : 0.0f;
        return MapLoan.toGetLoanDto(loanEntity, avgRating);
    }

    /**
     * Method for creating a new loan by the admin or the user who is creating the loan.
     * Taking loan reduces the available copies of the book
     *
     * @param loan - CreateLoanDto object containing information about the loan
     * @return CreateLoanResponseDto object containing information about the created loan
     * @throws EntityNotFoundException - if user or book with given id does not exist
     */
    @PreAuthorize("hasRole('ADMIN') or isAuthenticated() and this.isOwner(authentication.name, #loan.userId)")
    @Transactional
    public CreateLoanResponseDto create(CreateLoanDto loan) {
        UserEntity user = userRepository.findById(loan.getUserId()).orElseThrow(() -> new EntityNotFoundException("User with id: " + loan.getUserId() + " not found"));
        BookEntity book = bookRepository.findById(loan.getBookId()).orElseThrow(() -> new EntityNotFoundException("Book with id: " + loan.getBookId() + " not found"));
        LocalDate currentDate = LocalDate.now();
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setUser(user);
        loanEntity.setBook(book);
        loanEntity.setLoanDate(currentDate);
        loanEntity.setDueDate(loan.getDueDate());
        var newLoan = loanRepository.save(loanEntity);

        var bookEntity = bookRepository.findById(loan.getBookId()).orElseThrow(() -> new EntityNotFoundException("Book with id " + loan.getBookId() + " does not exist"));
        bookEntity.setAvailableCopies(bookEntity.getAvailableCopies() - 1);
        bookRepository.save(bookEntity);

        return new CreateLoanResponseDto(
                newLoan.getId(),
                newLoan.getUser().getId(),
                newLoan.getBook().getId(),
                newLoan.getLoanDate(),
                newLoan.getDueDate()
        );
    }

    /**
     * Method for returning the loan by the user who took the loan
     *
     * @param id - id of the loan
     * @throws EntityNotFoundException - if loan with given id or borrowed book does not exist
     */
    @PreAuthorize("hasRole('ADMIN') or isAuthenticated() and this.isOwner(authentication.name, #loan.userId)")
    @Transactional
    public void returnLoan(Long id) {
        var loanEntity = loanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Loan with id: " + id + " not found"));

        if (loanEntity.getReturnDate() == null) {
            loanEntity.setReturnDate(LocalDate.now());
            loanRepository.save(loanEntity);
        } else {
            log.info("Loan with id: {} already returned", id);
            throw new LoanAlreadyReturnedException("Loan with id: " + id + " is already returned");
        }

        var bookEntity = bookRepository.findById(loanEntity.getBook().getId()).orElseThrow(() -> new EntityNotFoundException("Book with id " + loanEntity.getBook().getId() + " does not exist"));
        bookEntity.setAvailableCopies(bookEntity.getAvailableCopies() + 1);
        bookRepository.save(bookEntity);
    }

    /**
     * Method for deleting a loan
     *
     * @param id - id of the loan
     * @throws EntityNotFoundException - if loan with given id does not exist
     */
    public void delete(Long id) {
        if (!loanRepository.existsById(id)) {
            log.info("Loan with id: {} not found", id);
            throw new EntityNotFoundException("Loan with id: " + id + " not found");
        }
        loanRepository.deleteById(id);
    }

}
