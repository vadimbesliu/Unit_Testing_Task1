package com.internship.bookstore.api.controller;

import com.internship.bookstore.api.dto.OrderRequestDto;
import com.internship.bookstore.api.dto.OrderResponseDto;
import com.internship.bookstore.api.dto.ReviewRequestDto;
import com.internship.bookstore.api.dto.ReviewResponseDto;
import com.internship.bookstore.api.exchange.Response;
import com.internship.bookstore.service.OrderService;
import com.internship.bookstore.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor

public class ReviewRestController {

    private final ReviewService reviewService;
    @PostMapping
    public ResponseEntity<Response<ReviewResponseDto>> createReview(
            @RequestBody
            @Valid ReviewRequestDto reviewRequestDto,
            Errors validationErrors) {

        if (validationErrors.hasErrors()) {
            throw new ValidationException(Objects.requireNonNull(validationErrors.getFieldError()).getDefaultMessage());
        }
        return ok(Response.build(reviewService.saveReview(reviewRequestDto)));
    }
    @PostMapping("/update")
    public ResponseEntity<Response<ReviewResponseDto>> updateReview(
            @RequestBody
            @Valid ReviewRequestDto reviewRequestDto,
            Errors validationErrors) {

        if (validationErrors.hasErrors()) {
            throw new ValidationException(Objects.requireNonNull(validationErrors.getFieldError()).getDefaultMessage());
        }
        return ok(Response.build(reviewService.updateReview(reviewRequestDto)));
    }


}
