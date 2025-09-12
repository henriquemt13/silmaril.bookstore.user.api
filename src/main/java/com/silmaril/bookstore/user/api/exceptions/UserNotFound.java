package com.silmaril.bookstore.user.api.exceptions;

public class UserNotFound extends NotFoundException {

    public UserNotFound(Long userId) {
        super(String.format("User ID [%d] not found", userId));
    }
}
