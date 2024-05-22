package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.exceptions.BookAlreadyExistsException;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.*;
import edu.lb.spring_networktechnologies.infrastructure.entities.BookEntity;
import edu.lb.spring_networktechnologies.infrastructure.mappings.MapBook;
import edu.lb.spring_networktechnologies.infrastructure.repositores.BookRepository;
import edu.lb.spring_networktechnologies.infrastructure.repositores.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static edu.lb.spring_networktechnologies.infrastructure.mappings.MapBook.getBookEntity;

@Service
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public BookService(BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    /**
     * Method for getting all books from the database using pagination
     *
     * @param page       - page number
     * @param size       - number of books per page
     * @param searchTerm - search term for filtering books by title
     * @return GetBooksPageDto object containing list of GetBookDto objects and pagination information
     */
    public GetBooksPageDto getAll(int page, int size, String searchTerm) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookEntity> booksPage;

        if (searchTerm != null && !searchTerm.isEmpty()) {
            booksPage = bookRepository.findAllByTitle(searchTerm, pageable);
        } else {
            booksPage = bookRepository.findAll(pageable);
        }

        List<GetBookDto> booksDto = booksPage.getContent().stream()
                .map(bookEntity -> {
                    Double averageRating = reviewRepository.calculateAverageRating(bookEntity.getId());
                    float avgRating = (averageRating != null) ? averageRating.floatValue() : 0.0f;
                    return MapBook.toGetBookDto(bookEntity, avgRating);
                })
                .toList();

        return new GetBooksPageDto(
                booksDto,
                booksPage.getNumber(),
                booksPage.getTotalPages(),
                booksPage.getTotalElements(),
                booksPage.hasNext());
    }

    /**
     * Method for getting a single book by its id
     *
     * @param id - id of the book
     * @return GetBookDto object containing information about the book
     * @throws EntityNotFoundException - if book with given id does not exist
     */
    public GetBookDto getOne(Long id) {
        var bookEntity = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " does not exist"));
        float rating = reviewRepository.calculateAverageRating(bookEntity.getId()).floatValue();

        return MapBook.toGetBookDto(bookEntity, rating);
    }

    /**
     * Method for creating a new book
     *
     * @param book - CreateBookDto object containing information about the book
     * @return CreateBookResponseDto object containing information about the created book
     * @throws BookAlreadyExistsException - if book with given ISBN already exists
     */
    public CreateBookResponseDto create(CreateBookDto book) {

        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new BookAlreadyExistsException("Book with ISBN " + book.getIsbn() + " already exists");
        }

        var bookEntity = getBookEntity(book);

        var newBook = bookRepository.save(bookEntity);
        return new CreateBookResponseDto(
                newBook.getId(),
                newBook.getIsbn(),
                newBook.getTitle(),
                newBook.getAuthor(),
                newBook.getPublisher(),
                newBook.getPublicationYear(),
                newBook.getAvailableCopies()
        );
    }

    /**
     * Method for updating a book
     *
     * @param id     - id of the book
     * @param copies - number of copies to add
     * @return AddBookResponseDto object containing information about the updated book
     * @throws EntityNotFoundException - if book with given id does not exist
     */
    public AddBookResponseDto add(Long id, Integer copies) {
        var bookEntity = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " does not exist"));
        bookEntity.setAvailableCopies(bookEntity.getAvailableCopies() + copies);
        bookRepository.save(bookEntity);
        return new AddBookResponseDto(bookEntity.getId(), bookEntity.getAvailableCopies());
    }

    /**
     * Method for deleting a book
     *
     * @param id - id of the book
     * @throws EntityNotFoundException - if book with given id does not exist
     */
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book with id " + id + " does not exist");
        }
        bookRepository.deleteById(id);
    }

    /**
     * Method for getting details of a single book by its id
     *
     * @param id - id of the book
     * @return GetBookDetailsDto object containing detailed information about the book
     * @throws EntityNotFoundException - if book with given id does not exist
     */
    public GetBookDetailsDto getDetails(Long id) {
        var bookEntity = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " does not exist"));
        Double averageRating = reviewRepository.calculateAverageRating(bookEntity.getId());
        float avgRating = (averageRating != null) ? averageRating.floatValue() : 0.0f;
        int ratings = reviewRepository.countRatings(bookEntity.getId());
        return MapBook.toGetBookDetails(bookEntity, avgRating, ratings);
    }

}
