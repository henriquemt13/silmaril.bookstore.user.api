package com.silmaril.bookstore.user.api.fixture;

import com.silmaril.bookstore.user.api.domain.model.User;
import java.time.OffsetDateTime;

public class UserFixture {


    public static User buildUser() {
        var now = OffsetDateTime.now();
        return new User(1L, "Test", now, "System", now, "System");
    }
}
