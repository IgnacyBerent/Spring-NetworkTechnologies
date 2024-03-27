package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.exceptions.CheckBindingExceptions;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.CreateBookDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.CreateBookResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.GetBookDto;
import edu.lb.spring_networktechnologies.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/getAll")
    public @ResponseBody List<GetBookDto> getAllBooks() {
        return bookService.getAll();
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED) //code 201
    public ResponseEntity<CreateBookResponseDto> create(@Valid @RequestBody CreateBookDto book, BindingResult bindingResult) {
        CheckBindingExceptions.check(bindingResult);
        var newBook = bookService.create(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public GetBookDto getBook(@PathVariable Long id) {
        return bookService.getOne(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
