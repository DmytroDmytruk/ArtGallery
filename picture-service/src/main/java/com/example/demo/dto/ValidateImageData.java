package com.example.demo.dto;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ImageDataValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateImageData {
    String message() default "Invalid image data, image must be Base64, and height amd width must be at least 100 px";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

@Component
class ImageDataValidator implements ConstraintValidator<ValidateImageData, byte[]> {
    private String message;

    @Override
    public void initialize(ValidateImageData constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(byte[] value, ConstraintValidatorContext context) {
        if(value == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        try{
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(value));
            if (image == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                return false;
            }
            return image.getWidth() >= 100 && image.getHeight() >= 100;
        }catch (IllegalArgumentException | IOException e){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
    }
}
