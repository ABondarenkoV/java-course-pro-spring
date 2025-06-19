package org.example;

import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
@ComponentScan
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        UserService userService = context.getBean(UserService.class);

        //Вывод всех пользователей
        System.out.println(
                userService.getAllUsers().stream()
                        .map(User::getUsername)
                        .collect(Collectors.joining(" , "))
        );
        System.out.println("Общее кол-во пользователей : " + userService.getAllUsers().size());

        //Создание нового пользователя
        //userService.createUser("Alina2");
        boolean toggle = false;
        for (int i = 0; i < 2; i++) {
            String newUsername = "User" + UUID.randomUUID().toString().substring(0, 5);
            if (toggle){
                userService.createUser(newUsername);
            }
        }

        //Поиск пользователя по ID
        //userService.getUsernameById(3L);
        for (int i = 1; i < 4; i++) {
            long randNum = new Random().nextInt(userService.getAllUsers().size());
            userService.getUsernameById(randNum).ifPresentOrElse(
                    name -> System.out.println("ID: " + randNum + " - " + "Username: " + name),
                    () -> System.out.println("Пользователь не найден с ID: " + randNum)
            );
        }

        //Изменение имени пользователя
        userService.changeUsername(10,"User2");

        //Удаление пользователя
        userService.deleteUser(11);

        context.close();
    }
}