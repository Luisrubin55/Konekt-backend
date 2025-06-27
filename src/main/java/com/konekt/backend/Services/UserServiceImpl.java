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
    public Optional<User> AddUser(User user) {
        Random random = new Random();
        String username = Strings.concat(user.getName(), user.getPaternalSurname() + random.nextInt(10000));
        user.setUsername(username);
        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);
        user.setActive(false);
        String cadena = String.valueOf(System.currentTimeMillis());
        Long token = Long.valueOf(cadena.substring(0, 9));
        user.setToken(token);
        return Optional.of(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> GetByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public Boolean ValidateToken(Long token) {
        Optional<User> userExists = userRepository.findByToken(token);
        if (userExists.isPresent()) {
            User userBD = userExists.orElseThrow();
            if (!userBD.getToken().equals(token))
                return false;
            userBD.setActive(true);
            userBD.setToken(null);
            userRepository.save(userBD);
            return true;
        }
        return false;
    }

    @Override
    public Long recoverPasswordToken(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User userBD = userOptional.orElseThrow();
            String cadena = String.valueOf(System.currentTimeMillis());
            Long token = Long.valueOf(cadena.substring(0, 9));
            userBD.setToken(token);
            User userSave = userRepository.save(userBD);
            return userSave.getToken();
        }
        return null;
    }

    public Optional<User> recoverPassword(Long token, User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        User userBD = userOptional.orElseThrow();
        if (userOptional.isPresent() && userBD.getToken() != null) {
            if (userBD.getToken().equals(token)) {
                String passwordEncoded = passwordEncoder.encode(user.getPassword());
                userBD.setPassword(passwordEncoded);
                userBD.setToken(null);
                return Optional.of(userRepository.save(userBD));
            }
        }
        return Optional.empty();
    }

}
