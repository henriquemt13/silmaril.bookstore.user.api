package com.silmaril.bookstore.user.api.domain.mapper;

import com.silmaril.bookstore.user.api.domain.dto.request.UserRequestDTO;
import com.silmaril.bookstore.user.api.domain.dto.response.SearchUserResponseDTO;
import com.silmaril.bookstore.user.api.domain.model.User;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User from(UserRequestDTO userRequestDTO);

    SearchUserResponseDTO from(User user);

    List<SearchUserResponseDTO> from(List<User> users);
}
