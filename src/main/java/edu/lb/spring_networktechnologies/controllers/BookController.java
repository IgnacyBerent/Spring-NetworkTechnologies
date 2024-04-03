package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.exceptions.CheckBindingExceptions;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.CreateBookDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.CreateBookResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.GetBookDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.GetBooksPageDto;
import edu.lb.spring_networktechnologies.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@PreAuthorize("isAuthenticated()")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<GetBooksPageDto> getAllBooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(bookService.getAll(page, size), HttpStatus.OK);
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED) //code 201
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateBookResponseDto> create(@Valid @RequestBody CreateBookDto book, BindingResult bindingResult) {
        CheckBindingExceptions.check(bindingResult);
        var newBook = bookService.create(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GetBookDto> getBook(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.getOne(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
