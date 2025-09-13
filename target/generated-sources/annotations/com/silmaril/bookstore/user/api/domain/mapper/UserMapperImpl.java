package com.silmaril.bookstore.user.api.domain.mapper;

import com.silmaril.bookstore.user.api.domain.dto.request.UserRequestDTO;
import com.silmaril.bookstore.user.api.domain.dto.response.SearchUserResponseDTO;
import com.silmaril.bookstore.user.api.domain.model.User;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-13T17:03:59-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User from(UserRequestDTO userRequestDTO) {
        if ( userRequestDTO == null ) {
            return null;
        }

        User user = new User();

        user.setName( userRequestDTO.getName() );

        return user;
    }

    @Override
    public SearchUserResponseDTO from(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        OffsetDateTime createdAt = null;
        String createdBy = null;
        OffsetDateTime updatedAt = null;
        String updatedBy = null;

        id = user.getId();
        name = user.getName();
        createdAt = user.getCreatedAt();
        createdBy = user.getCreatedBy();
        updatedAt = user.getUpdatedAt();
        updatedBy = user.getUpdatedBy();

        SearchUserResponseDTO searchUserResponseDTO = new SearchUserResponseDTO( id, name, createdAt, createdBy, updatedAt, updatedBy );

        return searchUserResponseDTO;
    }

    @Override
    public List<SearchUserResponseDTO> from(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<SearchUserResponseDTO> list = new ArrayList<SearchUserResponseDTO>( users.size() );
        for ( User user : users ) {
            list.add( from( user ) );
        }

        return list;
    }
}
