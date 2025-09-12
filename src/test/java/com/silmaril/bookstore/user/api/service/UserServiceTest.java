package com.silmaril.bookstore.user.api.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.silmaril.bookstore.user.api.domain.dto.request.UpdateUserDTO;
import com.silmaril.bookstore.user.api.exceptions.UserNotFound;
import com.silmaril.bookstore.user.api.fixture.UserFixture;
import com.silmaril.bookstore.user.api.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Nested
    @DisplayName("1. Create User")
    class createUser {

        @Test
        @DisplayName("1. Should create new user")
        void shouldCreateNewUser() {
            var user = UserFixture.buildUser();
            when(userRepository.save(any())).thenReturn(user);

            assertEquals(userService.save(user), user);
            verify(userRepository, times(1)).save(any());
        }
    }

    @Nested
    @DisplayName("1. Get User")
    class getUser {

        @Test
        @DisplayName("1. Should get user")
        void shouldGetUser() {
            var user = UserFixture.buildUser();
            when(userRepository.findById(any())).thenReturn(Optional.of(user));

            assertEquals(userService.findById(1L), Optional.of(user));
            verify(userRepository, times(1)).findById(any());
        }

        @Test
        @DisplayName("2. Should get users")
        void shouldGetUsers() {
            var user = UserFixture.buildUser();
            when(userRepository.findAll()).thenReturn(List.of(user));

            assertEquals(userService.findAll(), List.of(user));
            verify(userRepository, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("2. Update User")
    class updateUser {

        @Test
        @DisplayName("1. Should update user")
        void shouldUpdateUser() {
            var user = UserFixture.buildUser();
            when(userRepository.findById(any())).thenReturn(Optional.of(user));
            when(userRepository.save(any())).thenReturn(user);

            assertDoesNotThrow(() -> userService.update(1L, new UpdateUserDTO()));
            verify(userRepository, times(1)).save(any());
            verify(userRepository, times(1)).findById(any());
        }

        @Test
        @DisplayName("2. Should throw not found exception when trying to update an invalid user")
        void shouldThrowNotFoundWhenTryingToUpdateAnInvalidUser() {
            when(userRepository.findById(any())).thenReturn(Optional.empty());

            var dto = new UpdateUserDTO();
            assertThrows(UserNotFound.class, () -> userService.update(1L, dto));
            verify(userRepository, times(0)).save(any());
            verify(userRepository, times(1)).findById(any());
        }
    }

    @Nested
    @DisplayName("3. Delete User")
    class deleteUser {

        @Test
        @DisplayName("1. Should update user")
        void shouldDeleteUser() {
            var user = UserFixture.buildUser();
            when(userRepository.findById(any())).thenReturn(Optional.of(user));
            doNothing().when(userRepository).delete(any());

            assertDoesNotThrow(() -> userService.delete(1L));
            verify(userRepository, times(1)).delete(any());
            verify(userRepository, times(1)).findById(any());
        }

        @Test
        @DisplayName("2. Should throw not found exception when trying to delete an invalid user")
        void shouldThrowNotFoundWhenTryingToDeleteAnInvalidUser() {
            when(userRepository.findById(any())).thenReturn(Optional.empty());

            assertThrows(UserNotFound.class, () -> userService.delete(1L));
            verify(userRepository, times(0)).delete(any());
            verify(userRepository, times(1)).findById(any());
        }
    }
}