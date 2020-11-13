package com.internship.bookstore.service;


import com.internship.bookstore.api.dto.ReviewRequestDto;
import com.internship.bookstore.api.dto.ReviewResponseDto;
import com.internship.bookstore.model.Review;
import com.internship.bookstore.repository.BookRepository;
import com.internship.bookstore.repository.ReviewRepository;
import com.internship.bookstore.utils.exceptions.RecordNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.swing.text.html.Option;
import java.util.Optional;

import static com.internship.TestConstants.ID_ONE;
import static com.internship.TestConstants.SECOND_TEXT_REVIEW;
import static com.internship.bookstore.utils.BookTestUtils.BOOK_ONE;
import static com.internship.bookstore.utils.BookTestUtils.BOOK_TWO;
import static com.internship.bookstore.utils.ReviewTestUtils.*;
import static com.internship.bookstore.utils.UserTestUtils.USER_ONE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private TextValidationService textValidationService;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(reviewService, "messageBookNotFound",
                "Book with id %s was not found");
        ReflectionTestUtils.setField(reviewService, "reviewNotValidated",
                "Illegal review");

    }

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(bookRepository, reviewRepository);
    }

    @Test
    void shouldSaveReview() {
        final ReviewResponseDto expectedResponseDto = REVIEW_RESPONSE_DTO;
        when(bookRepository.findBookById(ID_ONE)).thenReturn(Optional.of(BOOK_ONE));
        when(textValidationService.validate(any(ReviewRequestDto.class))).thenReturn(true);
        when(userService.getUser()).thenReturn(USER_ONE);
        when(reviewRepository.save(any(Review.class))).thenReturn(REVIEW_ONE);

        final ReviewResponseDto actualResponseDto = reviewService.saveReview(REVIEW_REQUEST_DTO);
        assertAll(
                () -> assertEquals(expectedResponseDto.getBookId(), actualResponseDto.getBookId()),
                () -> assertEquals(expectedResponseDto.getReviewID(), actualResponseDto.getReviewID())
        );
        verify(reviewRepository, times(1)).save(any(Review.class));
        verify(textValidationService, times(1)).validate(any(ReviewRequestDto.class));
    }

    @Test
    void shouldReturnBookNotFound() {
        when(bookRepository.findBookById(ID_ONE)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> reviewService.saveReview(REVIEW_REQUEST_DTO));
    }

    @Test
    void shouldNotValidateReview() {
        when(bookRepository.findBookById(ID_ONE)).thenReturn(Optional.of(BOOK_ONE));
        when(textValidationService.validate(any(ReviewRequestDto.class))).thenReturn(false);

        assertThrows(RecordNotFoundException.class, () -> reviewService.saveReview(REVIEW_REQUEST_DTO_TWO));
    }

    @Test
    void shouldUpdateReview() {
        final ReviewResponseDto expectedResponseDto = REVIEW_RESPONSE_DTO;

        when(bookRepository.findBookById(ID_ONE)).thenReturn(Optional.of(BOOK_ONE));
        when(reviewRepository.findById(ID_ONE)).thenReturn(Optional.of(REVIEW_ONE));
        when(reviewRepository.save(any(Review.class))).thenReturn(REVIEW_ONE);

        final ReviewResponseDto actualResponseDto = reviewService.updateReview(REVIEW_REQUEST_DTO);

        assertAll(
                () -> assertEquals(expectedResponseDto.getReviewID(), actualResponseDto.getReviewID()),
                () -> assertEquals(expectedResponseDto.getBookId(), actualResponseDto.getBookId())
        );

        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void shouldReturnBookNotFoundOnUpdate() {
        when(bookRepository.findBookById(ID_ONE)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> reviewService.updateReview(REVIEW_REQUEST_DTO));
    }

    @Test
    void shouldReturnReviewNotFoundOnUpdate() {
        when(bookRepository.findBookById(ID_ONE)).thenReturn(Optional.of(BOOK_ONE));
        when(reviewRepository.findById(ID_ONE)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> reviewService.updateReview(REVIEW_REQUEST_DTO));
    }

}
