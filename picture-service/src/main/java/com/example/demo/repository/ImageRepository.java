package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
    void deleteImageById(Long id);
}
