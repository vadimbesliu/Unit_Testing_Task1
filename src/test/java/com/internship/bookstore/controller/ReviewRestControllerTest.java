package com.internship.bookstore.controller;

import com.internship.bookstore.api.controller.BookRestController;
import com.internship.bookstore.api.controller.ReviewRestController;

import com.internship.bookstore.api.dto.ReviewRequestDto;
import com.internship.bookstore.service.ReviewService;
import com.internship.bookstore.service.UserService;
import com.internship.it.controller.BaseController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static com.internship.bookstore.utils.BookTestUtils.BOOK_RESPONSE_DTO_ONE;
import static com.internship.bookstore.utils.ReviewTestUtils.REVIEW_REQUEST_DTO;
import static com.internship.bookstore.utils.ReviewTestUtils.REVIEW_RESPONSE_DTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewRestController.class)

public class ReviewRestControllerTest  extends BaseController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ReviewService reviewService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    public void shouldSaveReview() throws Exception {
        when(reviewService.saveReview(any(ReviewRequestDto.class))).thenReturn(REVIEW_RESPONSE_DTO);

        mockMvc.perform(post("/review")
        .contentType(MediaType.APPLICATION_JSON)
                .content(createExpectedBody(REVIEW_REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createExpectedBody(REVIEW_RESPONSE_DTO)));

        verify(reviewService).saveReview(any(ReviewRequestDto.class));
    }

    @Test
    @WithMockUser
    public void shouldUpdateReview() throws Exception{
        when(reviewService.updateReview(any(ReviewRequestDto.class))).thenReturn(REVIEW_RESPONSE_DTO);

        mockMvc.perform(post("/review/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createExpectedBody(REVIEW_REQUEST_DTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createExpectedBody(REVIEW_RESPONSE_DTO)));
    }



}
