package org.example.repository;

import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT username FROM User WHERE username LIKE CONCAT('%', :part, '%')")
    List<String> findUsernameByPart(@Param("part") String part);
}
