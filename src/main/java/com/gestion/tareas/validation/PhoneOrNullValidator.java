package com.gestion.tareas.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneOrNullValidator implements ConstraintValidator<PhoneOrNull, String> {
    @Override
    public void initialize(PhoneOrNull constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        return input == null || input.matches("\\d{9}");
    }
}
