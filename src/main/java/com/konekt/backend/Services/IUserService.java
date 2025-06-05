package com.konekt.backend.Services;

import java.util.List;

import com.konekt.backend.Entities.User;

public interface IUserService {
    List<User> GetAllUsers();
    User AddUser(User user);
    User GetByIdUser();
    Boolean ValidateToken(User user);
    boolean existsByEmail(String email);
}
