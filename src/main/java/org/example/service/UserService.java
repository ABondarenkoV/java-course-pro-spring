package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.User;
import org.example.exception.EntityNotFoundException;
import org.example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    log.info("Пользователь найден: ID = {}, username = {}", user.getId(), user.getUsername());
                    return user;
                })
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с ID " + id + " не найден"));
    }

    public void createUser(String username) {
        User user = new User();
        user.setUsername(username);
        userRepository.save(user);
        log.info("Пользователь {} успешно создан!", username);
    }

    public void changeUsername(long id, String newUsername) {
        User user = getUserById(id);

        if (user.getUsername().equals(newUsername)) {
            log.info("У пользователя уже установлено текущее имя: {}", newUsername);
            return;
        }

        user.setUsername(newUsername);
        log.info("Пользователь: {} изменил имя на: {}", user.getId(), newUsername);
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        User user = getUserById(id);
        userRepository.delete(user);
        log.info("Пользователь с ID: {} успешно удалён", id);
    }

    public void getUsernameByPart(String str) {
        List<String> usernames = userRepository.findUsernameByPart(str);
        log.info(String.join(" , ", usernames));
    }
}
