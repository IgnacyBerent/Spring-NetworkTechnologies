package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.entities.Book;
import edu.lb.spring_networktechnologies.repositores.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED) //code 201
    public @ResponseBody Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
