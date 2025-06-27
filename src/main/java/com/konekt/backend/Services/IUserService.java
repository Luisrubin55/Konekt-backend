package com.konekt.backend.Services;

import java.util.List;
import java.util.Optional;

import com.konekt.backend.Entities.User;

public interface IUserService {
    List<User> GetAllUsers();
    Optional<User> AddUser(User user);
    Optional<User> GetByEmail(String email);
    Boolean ValidateToken(Long token);
    boolean existsByEmail(String email);
    Long recoverPasswordToken(String email);
    Optional<User> recoverPassword(Long token, User user);
}
