package com.example.demo.dto;

import com.example.demo.repository.CategoryRepository;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CategoryValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateCategory {
    String message() default "Invalid category, it doesn`t exist in db";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

@Component
class CategoryValidator implements ConstraintValidator<ValidateCategory, String> {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void initialize(ValidateCategory constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return categoryRepository.existsByName(value);
    }
}
