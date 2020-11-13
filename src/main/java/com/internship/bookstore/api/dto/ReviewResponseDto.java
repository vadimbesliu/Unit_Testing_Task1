package com.internship.bookstore.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ReviewResponseDto {
    private Long reviewID;
    private Long bookId;
    private Long userID;
    private String textReview;
}
