package com.silmaril.bookstore.user.api.filter;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ErrorResponse {

    private LocalDateTime timestamp;
    private String details;

    public ErrorResponse (String errorMessage) {
        this.details = errorMessage;
        this.timestamp = LocalDateTime.now();
    }
}
