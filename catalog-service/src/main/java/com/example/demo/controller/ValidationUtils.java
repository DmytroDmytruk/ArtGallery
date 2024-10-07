package com.example.demo.controller;

import jakarta.validation.ValidationException;

import java.text.SimpleDateFormat;

public class ValidationUtils {

    private boolean isValidDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void validateDate(String date) {
        if(!isValidDate(date)){
            throw new ValidationException("Invalid date");
        }
    }
}
