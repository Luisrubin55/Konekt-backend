package com.konekt.backend.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.konekt.backend.Entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
