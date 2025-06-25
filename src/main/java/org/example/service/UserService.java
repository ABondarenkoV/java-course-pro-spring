package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements CommandLineRunner {
    //- Реализуйте в виде бина UserService, который позволяет: создавать, удалять,
    // получать одного, получать всех пользователей из базы да
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUsernameById(Long id) {
        return userRepository.findById(id);
    }

    public void createUser(String username) {
        userRepository.save(new User(null, username));
        log.info("Пользователь " + username + " успешно создан!");
    }

    public void changeUsername(long id, String newUsername) {
        Optional<User> userOpt = getUsernameById(id);
        if (userOpt.isPresent()) {
            if (!userOpt.get().getUsername().equals(newUsername)) {
                System.out.println("Пользователь: " + userOpt.get().getUsername() + " изменил имя на: " + newUsername);
                userRepository.save(new User(id, newUsername));

            } else
                System.out.println("У пользователя уже установлено текущее имя: " + newUsername);
        } else {
            System.out.println("Пользователь с ID " + id + " не найден.");
        }
    }

    public void deleteUser(long id) {
        Optional<User> userOpt = getUsernameById(id);
        if (userOpt.isPresent()) {
            userRepository.delete(userOpt.get());
            System.out.println("Пользователь с ID: " + id + " успешно удалён");
        } else {
            System.out.println("Пользователь с ID: " + id + " не найден");
        }
    }

    public void getUsernameByPart(String str) {
        List<String> usernames = userRepository.findUsernameByPart(str);
        log.info(String.join(" , ", usernames));
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(
                getAllUsers().stream()
                        .map(User::getUsername)
                        .collect(Collectors.joining(" , "))
        );

        log.info("Общее кол-во пользователей : {}", getAllUsers().size());

        //Поиск пользователя по ID
/*        System.out.println(getUsernameById(3L).get());
        System.out.println(getUsernameById(3L));*/

        //Создание нового пользователя
        //userService.createUser("Alina2");
        boolean toggle = false;
        for (int i = 0; i < 2; i++) {
            String newUsername = "User" + UUID.randomUUID().toString().substring(0, 5);
            if (toggle) {
                createUser(newUsername);
            }
        }

        for (int i = 1; i < 4; i++) {
            long randNum = new Random().nextInt(getAllUsers().size());
            getUsernameById(randNum).ifPresentOrElse(
                    name -> System.out.println("ID: " + randNum + " - " + "Username: " + name.getUsername()),
                    () -> System.out.println("Пользователь не найден с ID: " + randNum)
            );
        }

        //Изменение имени пользователя
        changeUsername(10, "User2");

        //Удаление пользователя
        deleteUser(10);

        getUsernameByPart("3");
    }

}
