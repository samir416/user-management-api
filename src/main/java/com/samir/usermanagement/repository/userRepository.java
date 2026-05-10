package com.samir.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.samir.usermanagement.entity.user;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface userRepository
        extends JpaRepository<user, Integer> {

    Optional<user> findByEmail(String email);

    List<user> findByNameContainingIgnoreCase(String name);

    @Query("SELECT u FROM user u WHERE u.name LIKE CONCAT(:name, '%')")
    List<user> findUsersByNameStartingWith(
            @Param("name") String name);
}