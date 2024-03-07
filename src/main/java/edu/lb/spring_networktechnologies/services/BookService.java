package edu.lb.spring_networktechnologies.services;

import edu.lb.spring_networktechnologies.dtos.book.BookCreateDTO;
import edu.lb.spring_networktechnologies.dtos.book.BookDTO;
import edu.lb.spring_networktechnologies.entities.Book;
import edu.lb.spring_networktechnologies.repositores.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book toBook(BookCreateDTO bookCreateDTO) {
        Book book = new Book();
        book.setTitle(bookCreateDTO.getTitle());
        book.setAuthor(bookCreateDTO.getAuthor());
        book.setIsbn(bookCreateDTO.getIsbn());
        book.setYear(bookCreateDTO.getYear());
        return book;
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public BookDTO toBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setPublisher(book.getPublisher());
        bookDTO.setYear(book.getYear());
        bookDTO.setAvailableCopies(book.getAvailableCopies());
        return bookDTO;
    }

    public Iterable<BookDTO> getAllBooks() {
        Iterable<Book> books = bookRepository.findAll();
        List<BookDTO> bookDTOs = new ArrayList<>();
        for (Book book : books) {
            bookDTOs.add(toBookDTO(book));
        }
        return bookDTOs;
    }

    public Book getBookById(Integer bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }
}
