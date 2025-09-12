package com.silmaril.bookstore.user.api.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserResponseDTO {

    private Long id;
    private String name;
}
