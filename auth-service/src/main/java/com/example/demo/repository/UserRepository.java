package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String username);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
