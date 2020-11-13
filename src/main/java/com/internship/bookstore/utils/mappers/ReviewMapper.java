package com.internship.bookstore.utils.mappers;

import com.internship.bookstore.api.dto.ReviewResponseDto;
import com.internship.bookstore.model.Review;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewMapper {
    public static final Function<Review, ReviewResponseDto> mapReviewToReviewResponseDto =
            review -> ReviewResponseDto.builder()
                    .reviewID(review.getId())
                    .textReview(review.getTextReview())
                    .bookId(review.getBook().getId())
                    .userID(review.getUser().getId()).build();

}
