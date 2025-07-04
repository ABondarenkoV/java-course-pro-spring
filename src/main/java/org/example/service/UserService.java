package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService  {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserId(Long id) {
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            log.info("Пользователь найден: ID = {}, username = {}", user.getId(), user.getUsername());
        } else {
            log.warn("Пользователь с ID = {} не найден", id);
        }

        return userOpt;
    }

    public void createUser(String username) {
        User user = new User();
        user.setUsername(username);
        userRepository.save(user);
        log.info("Пользователь {} успешно создан!", username);
    }

    public void changeUsername(long id, String newUsername) {
        Optional<User> userOpt = getUserId(id);
        if (userOpt.isPresent()) {
            if (!userOpt.get().getUsername().equals(newUsername)) {
                User user = new User();
                user.setUsername(newUsername);
                log.info("Пользователь: {} изменил имя на: {}", userOpt.get().getUsername(), newUsername);
                userRepository.save(user);
            } else
                log.info("У пользователя уже установлено текущее имя: {}", newUsername);
        }
    }

    public void deleteUser(long id) {
        Optional<User> userOpt = getUserId(id);
        if (userOpt.isPresent()) {
            userRepository.delete(userOpt.get());
            log.info("Пользователь с ID: {} успешно удалён", id);
        }
    }

    public void getUsernameByPart(String str) {
        List<String> usernames = userRepository.findUsernameByPart(str);
        log.info(String.join(" , ", usernames));
    }

/*    @Override
    public void run(String... args) throws Exception {
        log.info(
                getAllUsers().stream()
                        .map(User::getUsername)
                        .collect(Collectors.joining(" , "))
        );

        boolean toggle = false;
        for (int i = 0; i < 2; i++) {
            String newUsername = "User" + UUID.randomUUID().toString().substring(0, 5);
            if (toggle) {
                createUser(newUsername);
            }
        }

        for (int i = 1; i < 4; i++) {
            long randNum = new Random().nextInt(getAllUsers().size());
            getUserId(randNum);

        }

        changeUsername(9, "User2");

        deleteUser(8);

        getUsernameByPart("3");
    }*/

}
