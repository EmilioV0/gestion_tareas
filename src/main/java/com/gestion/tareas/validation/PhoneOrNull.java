package com.gestion.tareas.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneOrNullValidator.class)
@Documented
public @interface PhoneOrNull {
    String message() default "The phone number must be 9 digits long";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}