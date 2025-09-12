package com.silmaril.bookstore.user.api.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserDTO {

    @NotNull(message = "name is mandatory")
    private String name;
}
