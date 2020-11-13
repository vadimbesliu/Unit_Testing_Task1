package com.internship.bookstore.utils;

import com.internship.bookstore.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.internship.TestConstants.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class UserTestUtils {
    public static final User USER_ONE = User.builder()
            .id(ID_ONE)
            .email(AUTH_USER_EMAIL)
            .password(AUTH_USER_PASSWORD)
            .build();
}
