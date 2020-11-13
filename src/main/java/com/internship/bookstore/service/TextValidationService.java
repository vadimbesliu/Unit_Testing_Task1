package com.internship.bookstore.service;

import com.internship.bookstore.api.dto.ReviewRequestDto;
import com.internship.bookstore.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TextValidationService {

    public boolean validate(ReviewRequestDto reviewRequestDto) {
        if (reviewRequestDto.getReview().isEmpty() || (reviewRequestDto.getReview().contains("admin") ||
                reviewRequestDto.getReview().contains("free") || reviewRequestDto.getReview().contains("test")))
            return false;
        return true;
    }


}
