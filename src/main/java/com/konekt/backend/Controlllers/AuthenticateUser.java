package com.konekt.backend.Controlllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.konekt.backend.Entities.User;
import com.konekt.backend.Middlewares.DecodedJWTValidation;
import com.konekt.backend.Services.IUserService;
import com.konekt.backend.Validation.ValidationFields;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/user")
public class AuthenticateUser {

    @Autowired
    private ValidationFields validationFields;

    @Autowired
    private IUserService iUserService;

    @GetMapping()
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String authHeader) {

        if (!authHeader.startsWith("Bearer ")) {
            Map<String, String> message = new HashMap<>();
             message.put("message", "No autorizado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        String email = DecodedJWTValidation.decodedJWT(authHeader);
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.GetByEmail(email));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return validationFields.validation(result);
        }
        Optional<User> userOptional = iUserService.AddUser(user);
        User userBD = userOptional.orElseThrow();
        Map<String, String> message = new HashMap<>();
        if (userOptional.isPresent()) {
            message.put("email", userBD.getEmail());
            message.put("token", userBD.getToken().toString());
            message.put("message", "Usuario creado correctamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
        message.put("message", "Error al intentar crear el usuario");
        return ResponseEntity.badRequest().body(message);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateUser(@RequestBody Map<String, Long> token) {
        Map<String, String> message = new HashMap<>();
        Boolean validateToken = iUserService.ValidateToken(token.get("token"));

        if (validateToken) {
            message.put("message", "Cuenta confirmada, Puedes iniciar sesión");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
        }
        message.put("message", "Token no valido");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @PostMapping("/search-account")
    public ResponseEntity<?> recoverPasswordToken(@RequestBody Map<String, String> email) {
        Long token = iUserService.recoverPasswordToken(email.get("email"));
        Map<String, String> message = new HashMap<>();
        if (token == null) {
            message.put("message", "Email no pertenece a alguna cuenta registrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        message.put("token", token.toString());
        message.put("message", "Token enviado, revisa tu correo electronico");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
    }

    @PostMapping("/recover-password/{token}")
    public ResponseEntity<?> recoverPassword(@PathVariable Long token, @RequestBody User user) {
        Optional<User> userOptional = iUserService.recoverPassword(token, user);
        Map<String, String> message = new HashMap<>();
        if (userOptional.isPresent()) {
            message.put("message", "Contraseña restablecida puedes iniciar sesión");
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        message.put("message", "Error al validar el token");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}
