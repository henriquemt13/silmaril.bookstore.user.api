package com.silmaril.bookstore.user.api.service;

import com.silmaril.bookstore.user.api.domain.dto.request.UpdateUserDTO;
import com.silmaril.bookstore.user.api.domain.model.User;
import com.silmaril.bookstore.user.api.exceptions.UserNotFound;
import com.silmaril.bookstore.user.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private final UserRepository repository;

    public User save(User user) {
        user.setCreatedBy("System");
        user.setUpdatedBy("System");
        repository.save(user);
        return user;
    }

    public void update(Long userId, UpdateUserDTO userDTO) {
        var optUser = findById(userId);
        if (optUser.isEmpty()) {
            throw new UserNotFound(userId);
        }
        var user = optUser.get();
        user.setName(userDTO.getName());
        repository.save(user);
    }

    public Optional<User> findById(Long userId) {
        return repository.findById(userId);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void delete(Long userId) {
        var optUser = findById(userId);
        if (optUser.isEmpty()) {
            throw new UserNotFound(userId);
        }
        User user = optUser.get();
        repository.delete(user);
    }

}
