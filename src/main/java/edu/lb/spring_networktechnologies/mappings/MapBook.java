package edu.lb.spring_networktechnologies.mappings;

import edu.lb.spring_networktechnologies.infrastructure.dtos.book.GetBookDto;
import edu.lb.spring_networktechnologies.infrastructure.entities.BookEntity;

public class MapBook {
    public static GetBookDto toGetBookDto(BookEntity bookEntity) {
        return new GetBookDto(
                bookEntity.getId(),
                bookEntity.getIsbn(),
                bookEntity.getTitle(),
                bookEntity.getAuthor(),
                bookEntity.getPublisher(),
                bookEntity.getPublicationYear(),
                bookEntity.getAvailableCopies() > 0
        );
    }
}
