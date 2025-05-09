package com.example.demoSpringSec.repository;

import com.example.demoSpringSec.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String email, String username);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
