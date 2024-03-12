package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.infrastructure.dtos.book.BookCreateDTO;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.BookDTO;
import edu.lb.spring_networktechnologies.infrastructure.entities.Book;
import edu.lb.spring_networktechnologies.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED) //code 201
    public @ResponseBody BookDTO addBook(@RequestBody BookCreateDTO bookCreateDTO) {
        Book book = bookService.toBook(bookCreateDTO);
        Book savedBook = bookService.saveBook(book);
        return bookService.toBookDTO(savedBook);
    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }
}
