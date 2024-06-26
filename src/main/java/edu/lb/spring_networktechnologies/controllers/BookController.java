package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.exceptions.BookAlreadyExistsException;
import edu.lb.spring_networktechnologies.exceptions.CheckBindingExceptions;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.CreateBookDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.CreateBookResponseDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.GetBookDetailsDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.GetBooksPageDto;
import edu.lb.spring_networktechnologies.services.BookService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/books")
@PreAuthorize("isAuthenticated()")
@Tag(name = "Books", description = "Endpoints for books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Get all books from the database using pagination
     *
     * @param page       - page number
     * @param size       - number of books per page
     * @param searchTerm - search term for filtering books by title (optional)
     * @return GetBooksPageDto object containing list of GetBookDto objects and pagination information
     */
    @GetMapping("/getAll")
    @ApiResponse(responseCode = "200", description = "Books found")
    public ResponseEntity<GetBooksPageDto> getAllBooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String searchTerm) {
        return new ResponseEntity<>(bookService.getAll(page, size, searchTerm), HttpStatus.OK);
    }

    /**
     * Create a new book
     *
     * @param book          - CreateBookDto object containing information about the book
     * @param bindingResult - BindingResult object containing information about the validation
     * @return CreateBookResponseDto object containing information about the book
     * @throws ResponseStatusException    - if there are any validation errors
     * @throws BookAlreadyExistsException - if the book already exists
     */
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED) //code 201
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
                    @ApiResponse(responseCode = "409", description = "Book already exists"),
                    @ApiResponse(responseCode = "201", description = "Book created"),
            }
    )
    public ResponseEntity<CreateBookResponseDto> create(@Valid @RequestBody CreateBookDto book, BindingResult bindingResult) {
        CheckBindingExceptions.check(bindingResult);
        var newBook = bookService.create(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    /**
     * Get a book details by book id
     *
     * @param id - id of the book
     * @return GetBookDetailsDto object containing details about the book
     * @throws EntityNotFoundException - if the book does not exist
     */
    @GetMapping("/details/{id}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Book details found", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content),
            }
    )
    public ResponseEntity<GetBookDetailsDto> getBookDetails(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.getDetails(id), HttpStatus.OK);
    }

    /**
     * Delete a book by its id
     *
     * @param id - id of the book
     * @return ResponseEntity with no content
     * @throws EntityNotFoundException - if the book does not exist
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Book deleted", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content),
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
