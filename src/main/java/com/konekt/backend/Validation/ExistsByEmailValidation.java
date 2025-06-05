package com.konekt.backend.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.konekt.backend.Services.IUserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistsByEmailValidation implements ConstraintValidator<ExistsByEmail, String>{

    @Autowired
    private IUserService iUserService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
       if (iUserService == null) return true;
       return !iUserService.existsByEmail(email);
    }
    
}
