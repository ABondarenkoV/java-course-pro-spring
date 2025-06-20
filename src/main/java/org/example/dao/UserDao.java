package org.example.dao;

import org.example.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {
    // Реализуйте в виде бина класс UserDao который позволяет выполнять CRUD операции над пользователями
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    public void create(String username) {
        String sql = "INSERT INTO users (username) VALUES (?)";
        jdbcTemplate.update(sql, username);
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> result = jdbcTemplate.query(sql, new UserMapper(), id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.getFirst());
    }

    public void update(User user) {
        String sql = "UPDATE users SET username = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getUsername(), user.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
