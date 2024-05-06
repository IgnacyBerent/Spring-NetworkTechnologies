package edu.lb.spring_networktechnologies.infrastructure.mappings;

import edu.lb.spring_networktechnologies.infrastructure.dtos.book.GetBookDetailsDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.book.GetBookDto;
import edu.lb.spring_networktechnologies.infrastructure.entities.BookEntity;

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
                bookEntity.getAvailableCopies() > 0
        );
    }
}
