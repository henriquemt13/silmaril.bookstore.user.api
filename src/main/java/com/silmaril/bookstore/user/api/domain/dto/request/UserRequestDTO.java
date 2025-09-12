package com.silmaril.bookstore.user.api.domain.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotNull(message = "name should not be null")
    @NotEmpty(message = "name should not be empty")
    private String name;
}
