package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.exceptions.AlreadyExistsException;
import edu.lb.spring_networktechnologies.exceptions.NotFoundException;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.*;
import edu.lb.spring_networktechnologies.infrastructure.entities.BookEntity;
import edu.lb.spring_networktechnologies.infrastructure.mappings.MapBook;
import edu.lb.spring_networktechnologies.infrastructure.repositores.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public GetBooksPageDto getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookEntity> booksPage = bookRepository.findAll(pageable);
        List<GetBookDto> booksDto = booksPage.getContent().stream()
                .map(MapBook::toGetBookDto)
                .toList();

        return new GetBooksPageDto(
                booksDto,
                booksPage.getNumber(),
                booksPage.getTotalPages(),
                booksPage.getTotalElements(),
                booksPage.hasNext());
    }

    public GetBookDto getOne(Long id) {
        var bookEntity = bookRepository.findById(id).orElseThrow(NotFoundException::book);

        return MapBook.toGetBookDto(bookEntity);
    }

    public CreateBookResponseDto create(CreateBookDto book) {

        if (bookRepository.existsByIsbn(book.getIsbn())) {
            log.info("Book with given ISBN already exists");
            throw AlreadyExistsException.bookByIsbn(book.getIsbn());
        }

        var bookEntity = new BookEntity();
        bookEntity.setIsbn(book.getIsbn());
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setPublisher(book.getPublisher());
        bookEntity.setPublicationYear(book.getPublicationYear());
        bookEntity.setAvailableCopies(book.getAvailableCopies());
        bookEntity.setReviews(new ArrayList<>());
        bookEntity.setLoaned(new ArrayList<>());

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

    public AddBookResponseDto add(Long id, Integer copies) {
        var bookEntity = bookRepository.findById(id).orElseThrow(NotFoundException::book);
        bookEntity.setAvailableCopies(bookEntity.getAvailableCopies() + copies);
        bookRepository.save(bookEntity);
        return new AddBookResponseDto(bookEntity.getId(), bookEntity.getAvailableCopies());
    }

    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            log.info("Book with given id not found");
            throw NotFoundException.book();
        }
        bookRepository.deleteById(id);
    }

}
