package com.example.demo.service;

import java.time.LocalDate;
import java.util.*;

import com.example.demo.controller.proxy.AuthProxy;
import com.example.demo.dto.CategoryDTO;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PictureDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Image;
import com.example.demo.entity.Likes;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.LikesRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PictureService {

	private final String ADMIN_USERNAME = "admin";

	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LikesRepository likesRepository;
	@Autowired
	private AuthProxy proxy;


	@Transactional
	public void setLike(long imageId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("username doesn`t exists"));
        Image image = imageRepository.findById(imageId).orElseThrow(
                () -> new NoSuchElementException("username doesn`t exists"));
        if(likesRepository.existsByUserAndImage(user, image)){
            throw new IllegalStateException("like already exists");
        }
		Likes like = Likes.builder()
				.user(user)
				.image(image)
				.build();
		try {
			likesRepository.save(like);
		} catch (Exception e){
			throw new IllegalStateException("unable to add like");
		}
    }

	@Transactional
	public void deleteLike(long imageId, String username) {
		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new NoSuchElementException("username doesn`t exists"));
		Image image = imageRepository.findById(imageId).orElseThrow(
				() -> new NoSuchElementException("username doesn`t exists"));
		if(!likesRepository.existsByUserAndImage(user, image)){
			throw new IllegalStateException("like doesn`t exists");
		}
		try{
			likesRepository.deleteByUserAndImage(user, image);
		}catch(Exception e){
			throw new IllegalStateException("unable to delete like");
		}
	}

	@Transactional
	public void savePicture(PictureDTO pictureDTO, String username) {
		Category category = categoryRepository.findByName(pictureDTO.getCategory()).orElseThrow(
				() -> new NoSuchElementException("category doesn`t exists"));
		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new NoSuchElementException("username doesn`t exists"));
		Image image = Image.builder()
				.category(category)
				.imageData(pictureDTO.getImageData())
				.uploadDate(new Date())
				.user(user)
				.description(pictureDTO.getDescription())
				.name(pictureDTO.getName())
				.likes(new ArrayList<>())
				.build();
		try {
			imageRepository.save(image);
		} catch (Exception e){
			throw new IllegalStateException("unable to add picture");
		}
	}

	@Transactional
	public void deletePicture(long imageId, String username) {
		Image image = imageRepository.findById(imageId).orElseThrow(
				() -> new NoSuchElementException("image doesn`t exists"));
		if(image.getUser().getUsername().equals(username)){
			imageRepository.delete(image);
		} else {
			throw new IllegalStateException("unable to delete picture");
		}
	}

	@Transactional
	public void addCategory(CategoryDTO categoryDTO, String username) {
		if(!username.equals(ADMIN_USERNAME)){
			throw new IllegalStateException("you don`t have rights to add this category");
		}
		if(categoryRepository.existsByName(categoryDTO.getName())){
			throw new IllegalStateException("category already exists");
		}
		Category category = Category.builder()
				.name(categoryDTO.getName())
				.description(categoryDTO.getDescription())
				.illustrationData(categoryDTO.getImage())
				.build();
		try {
			categoryRepository.save(category);
		}catch (Exception e){
			throw new IllegalStateException("unable to add category");
		}
	}

	@Transactional
	public void deleteCategory(String name, String username) {
		if(!username.equals(ADMIN_USERNAME)){
			throw new IllegalStateException("you don`t have rights to add this category");
		}
		Category category = categoryRepository.findByName(name).orElseThrow(
				() -> new NoSuchElementException("category doesn`t exists"));
		try{
			categoryRepository.delete(category);
		} catch (Exception e){
			throw new IllegalStateException("unable to delete category");
		}
	}

	public long getLikesCount(long imageId) {
		Optional<Image> image = imageRepository.findById(imageId);
		return likesRepository.countByImage(image.orElse(null));
	}

	public String getUsername(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
		return proxy.getUsername(token);
	}
}
