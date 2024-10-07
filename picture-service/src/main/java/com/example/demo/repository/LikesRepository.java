package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Image;
import com.example.demo.entity.Likes;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
	Long countByImage(Image image);
	Boolean existsByUserAndImage(User user, Image image);
	void deleteByUserAndImage(User user, Image image);
}
