package com.internship.bookstore.service;

import com.internship.bookstore.api.dto.ReviewRequestDto;
import com.internship.bookstore.api.dto.ReviewResponseDto;
import com.internship.bookstore.model.Book;
import com.internship.bookstore.model.Review;
import com.internship.bookstore.model.WishList;
import com.internship.bookstore.repository.BookRepository;
import com.internship.bookstore.repository.ReviewRepository;
import com.internship.bookstore.utils.exceptions.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.internship.bookstore.utils.mappers.ReviewMapper.mapReviewToReviewResponseDto;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final UserService userService;
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final TextValidationService textValidationService;

    @Value("Book with id %s was not found")
    private String messageBookNotFound;

    @Value("Illegal review")
    private String reviewNotValidated;

    @Transactional
    public ReviewResponseDto saveReview(ReviewRequestDto reviewRequestDto) {
        log.warn("Saving the review");

        Book book = bookRepository.findBookById(reviewRequestDto.getBookId()).orElseThrow(() -> {
            log.warn("Book with id [{}] was not found in the database", reviewRequestDto.getBookId());
            return new RecordNotFoundException(format(messageBookNotFound, reviewRequestDto.getBookId()));
        });
        if (textValidationService.validate(reviewRequestDto)) {
            Review review = new Review();
            review.setBook(book);
            review.setUser(userService.getUser());
            review.setTextReview(reviewRequestDto.getReview());
            review = reviewRepository.save(review);
            return mapReviewToReviewResponseDto.apply(review);
        }
        throw new RecordNotFoundException(format(reviewNotValidated, reviewRequestDto.getReview()));
    }

    @Transactional
    public ReviewResponseDto updateReview(ReviewRequestDto reviewRequestDto) {
        log.warn("Update the review");

        Book book = bookRepository.findBookById(reviewRequestDto.getBookId()).orElseThrow(() -> {
            log.warn("Book with id [{}] was not found in the database", reviewRequestDto.getBookId());
            return new RecordNotFoundException(format(messageBookNotFound, reviewRequestDto.getBookId()));
        });

        Review review = reviewRepository.findById(reviewRequestDto.getReviewID()).orElseThrow(() -> {
            log.warn("Review with id [{}] was never created", reviewRequestDto.getReviewID());
            return new RecordNotFoundException(format(messageBookNotFound, reviewRequestDto.getReviewID()));
        });
        review.setTextReview(reviewRequestDto.getReview());
        review=reviewRepository.save(review);

        return mapReviewToReviewResponseDto.apply(review);
    }


}
