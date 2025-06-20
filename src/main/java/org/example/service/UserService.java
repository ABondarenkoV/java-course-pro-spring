package org.example.service;

import org.example.dao.UserDao;
import org.example.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    //- Реализуйте в виде бина UserService, который позволяет: создавать, удалять,
    // получать одного, получать всех пользователей из базы да
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public Optional<String> getUsernameById(Long id) {
        return userDao.findById(id).map(User::getUsername);
    }

    public void createUser(String username) {
        userDao.create(username);
        System.out.println("Пользователь " + username + " успешно создан!");
    }

    public void changeUsername(long id, String newUsername) {
        Optional<User> userOpt = userDao.findById(id);
        if (userOpt.isPresent()) {
            if(!userOpt.get().getUsername().equals(newUsername)){
                System.out.println("Пользователь: " + userOpt.get().getUsername() + " изменил имя на: " + newUsername);
                userDao.update(new User(id, newUsername));
            }else
                System.out.println("У пользователя уже установлено текущее имя: " + newUsername);
        } else {
            System.out.println("Пользователь с ID " + id + " не найден.");
        }
    }

    public void deleteUser(long id) {
        Optional<User> userOpt = userDao.findById(id);
        if (userOpt.isPresent()) {
            userDao.delete(id);
            System.out.println("Пользователь с ID: " + id + " успешно удалён");
        } else {
            System.out.println("Пользователь с ID: " + id + " не найден");
        }
    }
}
