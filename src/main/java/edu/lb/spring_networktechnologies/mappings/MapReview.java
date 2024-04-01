package edu.lb.spring_networktechnologies.mappings;

import edu.lb.spring_networktechnologies.infrastructure.dtos.review.GetReviewDto;
import edu.lb.spring_networktechnologies.infrastructure.entities.ReviewEntity;

public class MapReview {
    public static GetReviewDto toGetReviewDto(ReviewEntity reviewEntity) {
        return new GetReviewDto(
                reviewEntity.getId(),
                MapBook.toGetBookDto(reviewEntity.getBook()),
                MapUser.toGetUserDto(reviewEntity.getUser()),
                reviewEntity.getRating(),
                reviewEntity.getComment(),
                reviewEntity.getReviewDate()
        );
    }
}
