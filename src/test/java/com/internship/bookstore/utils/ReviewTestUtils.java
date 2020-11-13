package com.internship.bookstore.utils;

import com.internship.bookstore.api.dto.ReviewRequestDto;
import com.internship.bookstore.api.dto.ReviewResponseDto;
import com.internship.bookstore.model.Review;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.internship.TestConstants.*;
import static com.internship.bookstore.utils.BookTestUtils.BOOK_ONE;
import static com.internship.bookstore.utils.UserTestUtils.USER_ONE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewTestUtils {
    public static final ReviewResponseDto REVIEW_RESPONSE_DTO = ReviewResponseDto
            .builder()
            .reviewID(ID_ONE)
            .bookId(ID_ONE)
            .userID(ID_ONE)
            .textReview(TEXT_REVIEW)
            .build();
    public static final ReviewRequestDto REVIEW_REQUEST_DTO= ReviewRequestDto
            .builder()
            .reviewID(ID_ONE)
            .bookId(ID_ONE)
            .userID(ID_ONE)
            .review(TEXT_REVIEW)
            .build();

    public static final ReviewRequestDto REVIEW_REQUEST_DTO_TWO= ReviewRequestDto
            .builder()
            .reviewID(ID_ONE)
            .bookId(ID_ONE)
            .userID(ID_ONE)
            .review(SECOND_TEXT_REVIEW)
            .build();


    public static final Review REVIEW_ONE = Review
            .builder()
            .id(ID_ONE)
            .textReview(TEXT_REVIEW)
            .book(BOOK_ONE)
            .user(USER_ONE)
            .build();


}
