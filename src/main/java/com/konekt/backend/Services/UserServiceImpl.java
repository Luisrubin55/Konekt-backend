package com.konekt.backend.Services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.konekt.backend.Entities.User;
import com.konekt.backend.Repositories.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> GetAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User AddUser(User user) {
        Random random = new Random();
        String username = Strings.concat(user.getName(), user.getPaternalSurname() + random.nextInt(10000));
        user.setUsername(username);
        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);
        user.setActive(false);
        String cadena = String.valueOf(System.currentTimeMillis());
        Long token = Long.valueOf(cadena.substring(0, 9));
        user.setToken(token);
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User GetByIdUser() {
        throw new UnsupportedOperationException("Unimplemented method 'GetByIdUser'");
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public Boolean ValidateToken(User user) {
        Optional<User> userExists = userRepository.findByEmail(user.getEmail());
        if (userExists.isPresent()) {
            User userBD = userExists.orElseThrow();
            if (!userBD.getToken().equals(user.getToken())) return false;
            userBD.setActive(true);
            userBD.setToken(null);
            userRepository.save(userBD);
            return true;
        }
        return false;
    }

}
