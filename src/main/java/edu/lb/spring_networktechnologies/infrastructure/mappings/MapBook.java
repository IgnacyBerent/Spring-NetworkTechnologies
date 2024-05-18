package edu.lb.spring_networktechnologies.infrastructure.mappings;

import edu.lb.spring_networktechnologies.infrastructure.dtos.book.CreateBookDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.GetBookDetailsDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.GetBookDto;
import edu.lb.spring_networktechnologies.infrastructure.entities.BookEntity;

import java.util.ArrayList;

public class MapBook {
    public static GetBookDto toGetBookDto(BookEntity bookEntity, float rating) {
        return new GetBookDto(
                bookEntity.getId(),
                bookEntity.getImg(),
                bookEntity.getTitle(),
                bookEntity.getAuthor(),
                rating,
                bookEntity.getAvailableCopies() > 0
        );
    }

    public static GetBookDetailsDto toGetBookDetails(BookEntity bookEntity, float rating, int ratingCount) {
        return new GetBookDetailsDto(
                bookEntity.getId(),
                bookEntity.getImg(),
                bookEntity.getTitle(),
                bookEntity.getAuthor(),
                bookEntity.getIsbn(),
                bookEntity.getPublicationYear(),
                bookEntity.getPublisher(),
                rating,
                ratingCount,
                bookEntity.getGenre(),
                bookEntity.getSummary(),
                bookEntity.getAvailableCopies()
        );
    }

    public static BookEntity getBookEntity(CreateBookDto book) {
        var bookEntity = new BookEntity();
        bookEntity.setImg(book.getImg());
        bookEntity.setIsbn(book.getIsbn());
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setPublisher(book.getPublisher());
        bookEntity.setPublicationYear(book.getPublicationYear());
        bookEntity.setAvailableCopies(book.getAvailableCopies());
        bookEntity.setGenre(book.getGenre());
        bookEntity.setSummary(book.getSummary());
        bookEntity.setReviews(new ArrayList<>());
        bookEntity.setLoaned(new ArrayList<>());
        return bookEntity;
    }
}
