package com.konekt.backend.Validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class ValidationFields {
    public ResponseEntity<?> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El " + error.getField() + " "  + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
