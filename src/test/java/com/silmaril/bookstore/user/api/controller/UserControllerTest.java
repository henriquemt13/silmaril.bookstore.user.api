package com.silmaril.bookstore.user.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.silmaril.bookstore.user.api.config.TestConfig;
import com.silmaril.bookstore.user.api.domain.mapper.UserMapper;
import com.silmaril.bookstore.user.api.exceptions.UserNotFound;
import com.silmaril.bookstore.user.api.filter.ControllerAdvisor;
import com.silmaril.bookstore.user.api.fixture.UserFixture;
import com.silmaril.bookstore.user.api.service.UserService;
import com.silmaril.bookstore.user.api.utils.FileUtils;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(UserController.class)
@Import({UserController.class, ControllerAdvisor.class})
@ContextConfiguration(classes = TestConfig.class)
class UserControllerTest {

    private static final String PATH = "/api/v1/user";

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserMapper mapper;

    @Nested
    @DisplayName("1. Create User")
    class createUser {

        @Test
        @DisplayName("1. Should create new user")
        void saveShouldRunAsExpected() throws Exception {
            when(userService.save(any())).thenReturn(UserFixture.buildUser());
            when(mapper.from(UserFixture.buildUser())).thenReturn(UserFixture.buildSearchUser());
            var result = mvc.perform(
                  post(PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(FileUtils.readBytesFromClassLoader(
                              "json/request/json-user-create-request.json"))
            ).andExpect(status().isCreated()).andReturn();

            assertEquals(FileUtils.readFileFromClassLoader(
                        "json/expected/json-user-create-expected.json"),
                  result.getResponse().getContentAsString());
            verify(userService, times(1)).save(any());
        }

        @Test
        @DisplayName("1. Should validate creation inputs")
        void saveShouldValidateInvalidInputs() throws Exception {
            var result = mvc.perform(
                  post(PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(FileUtils.readBytesFromClassLoader(
                              "json/request/json-user-create-invalid-value-request.json"))
            ).andExpect(status().isBadRequest()).andReturn();

            verify(userService, times(0)).save(any());
        }
    }

    @Nested
    @DisplayName("2. Search User")
    class searchUser {

        @Test
        @DisplayName("1. Should find user by id")
        void findByIdShouldRunAsExpected() throws Exception {
            long id = 1L;
            when(userService.findById(anyLong())).thenReturn(Optional.of(UserFixture.buildUser()));
            when(mapper.from(UserFixture.buildUser())).thenReturn(UserFixture.buildSearchUser());
            var result = mvc.perform(
                  get(PATH + "/" + id)

                        .contentType(MediaType.APPLICATION_JSON_VALUE)
            ).andExpect(status().isOk()).andReturn();

            assertEquals(FileUtils.readFileFromClassLoader(
                        "json/expected/json-user-create-expected.json"),
                  result.getResponse().getContentAsString());
            verify(userService, times(1)).findById(anyLong());

        }

        @Test
        @DisplayName("2. Should find all users")
        void findAllShouldRunAsExpected() throws Exception {
            long id = 1L;
            when(userService.findAll()).thenReturn(List.of(UserFixture.buildUser()));
            when(mapper.from(List.of(UserFixture.buildUser()))).thenReturn(
                  List.of(UserFixture.buildSearchUser()));
            var result = mvc.perform(
                  get(PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
            ).andExpect(status().isOk()).andReturn();

            assertEquals(FileUtils.readFileFromClassLoader(
                        "json/expected/json-user-find-all-expected.json"),
                  result.getResponse().getContentAsString());
            verify(userService, times(1)).findAll();
        }

        @Test
        @DisplayName("3. Should throw not found when trying find user by id")
        void findByIdShouldThrowNotFoundException() throws Exception {
            long id = 1L;
            when(userService.findById(anyLong())).thenReturn(Optional.empty());

            var result = mvc.perform(
                  get(PATH + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
            ).andExpect(status().isNotFound()).andReturn();

            verify(userService, times(1)).findById(anyLong());
        }
    }

    @Nested
    @DisplayName("3. Update User")
    class updateUser {

        @Test
        @DisplayName("1. Should update user")
        void updateShouldRunAsExpected() throws Exception {
            long id = 1L;
            doNothing().when(userService).update(any(), any());
            var result = mvc.perform(
                  put(PATH + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(FileUtils.readBytesFromClassLoader(
                              "json/request/json-user-create-request.json"))
            ).andExpect(status().isOk()).andReturn();

            verify(userService, times(1)).update(anyLong(), any());
        }

        @Test
        @DisplayName("2. Should throw not found when trying update user by id")
        void updateShouldThrowNotFoundException() throws Exception {
            long id = 1L;
            doThrow(UserNotFound.class).when(userService).update(anyLong(), any());

            var result = mvc.perform(
                  put(PATH + "/" + id)
                        .content(FileUtils.readBytesFromClassLoader(
                              "json/request/json-user-create-request.json"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
            ).andExpect(status().isNotFound()).andReturn();

            verify(userService, times(1)).update(anyLong(), any());
        }
    }

    @Nested
    @DisplayName("4. Delete User")
    class deleteUser {

        @Test
        @DisplayName("1. Should delete user")
        void updateShouldRunAsExpected() throws Exception {
            long id = 1L;
            doNothing().when(userService).delete(any());
            var result = mvc.perform(
                  delete(PATH + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
            ).andExpect(status().isOk()).andReturn();

            verify(userService, times(1)).delete(any());
        }

        @Test
        @DisplayName("2. Should throw not found when trying to delete user by id")
        void updateShouldThrowNotFoundException() throws Exception {
            long id = 1L;
            doThrow(UserNotFound.class).when(userService).delete(anyLong());

            var result = mvc.perform(
                  delete(PATH + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
            ).andExpect(status().isNotFound()).andReturn();

            verify(userService, times(1)).delete(anyLong());
        }
    }
}