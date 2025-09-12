package com.silmaril.bookstore.user.api.controller;

import com.silmaril.bookstore.user.api.domain.dto.request.UpdateUserDTO;
import com.silmaril.bookstore.user.api.domain.dto.request.UserRequestDTO;
import com.silmaril.bookstore.user.api.domain.dto.response.SearchUserResponseDTO;
import com.silmaril.bookstore.user.api.domain.mapper.UserMapper;
import com.silmaril.bookstore.user.api.exceptions.UserNotFound;
import com.silmaril.bookstore.user.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    @ApiResponse(description = "User Response", responseCode = "201")
    @Operation(summary = "Create user", description = """
          # Create new user
          ---
          """)
    public ResponseEntity<SearchUserResponseDTO> createuser(
          @RequestBody @Valid UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
              .body(mapper.from(service.save(mapper.from(userRequestDTO))));
    }

    @PutMapping("/{userId}")
    @ApiResponse(description = "Void", responseCode = "200")
    @Operation(summary = "Update user by id", description = """
          # Update user by ID
          ---
          notes:
          - user must exist on database;
          """)
    public ResponseEntity<Void> updateuser(@PathVariable("userId") Long userId,
          @RequestBody @Valid UpdateUserDTO userDTO) {
        service.update(userId, userDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{userId}")
    @ApiResponse(description = "User response", responseCode = "200")
    @Operation(summary = "Get user by id", description = """
          # Searches User by Id
          ---
          notes:
          - user must exist on database;
          """)
    public ResponseEntity<SearchUserResponseDTO> getUserById(@PathVariable("userId") Long userId) {
        var user = service.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFound(userId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.from(user.get()));
    }

    @GetMapping
    @ApiResponse(description = "User response", responseCode = "200")
    @Operation(summary = "Find All user", description = """
          # search all users
          ---
          """)
    public ResponseEntity<List<SearchUserResponseDTO>> getAllusers() {
        return ResponseEntity.status(HttpStatus.OK).body(mapper.from(service.findAll()));
    }

    @DeleteMapping("/{userId}")
    @ApiResponse(description = "Void", responseCode = "200")
    @Operation(summary = "Delete user by id", description = """
          # Delete user by ID
          ---
          """)
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") Long userId) {
        service.delete(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
