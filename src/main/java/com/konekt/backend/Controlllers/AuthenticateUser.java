package com.konekt.backend.Controlllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.konekt.backend.Entities.User;
import com.konekt.backend.Services.IUserService;
import com.konekt.backend.Validation.ValidationFields;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(value = "http://localhost:5173/")
public class AuthenticateUser {

    @Autowired
    private ValidationFields validationFields;

    @Autowired
    private IUserService iUserService;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.GetAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return validationFields.validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(iUserService.AddUser(user));
    }

    @PostMapping("/validate/{token}")
    public ResponseEntity<?> valideUser(@PathVariable Long token, @RequestBody Map<String, String> email) {
        User user = new User();
        Map<String, String> message = new HashMap<>();
        user.setEmail(email.get("email"));
        user.setToken(token);
        Boolean validateToken = iUserService.ValidateToken(user);

        if (validateToken) {
            message.put("message", "Cuenta confirmada, Puedes iniciar sesión");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
        }
        message.put("message", "Token no valido");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}
