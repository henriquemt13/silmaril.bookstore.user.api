package com.silmaril.bookstore.user.api.fixture;

import com.silmaril.bookstore.user.api.domain.dto.response.SearchUserResponseDTO;
import com.silmaril.bookstore.user.api.domain.model.User;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class UserFixture {


    public static User buildUser() {
        var day = OffsetDateTime.of(2025, 12, 9, 0, 0, 0, 0, ZoneOffset.MIN);
        return new User(1L, "Test", day, "System", day, "System");
    }

    public static SearchUserResponseDTO buildSearchUser() {
        var day = OffsetDateTime.of(2025, 12, 9, 0, 0, 0, 0, ZoneOffset.MIN);
        return new SearchUserResponseDTO(1L, "Test", day, "System", day, "System");
    }
}
