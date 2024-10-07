package com.example.demo.controller;

import com.example.demo.dto.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.PictureDTO;
import com.example.demo.service.PictureService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/picture")
public class PictureController {
	@Autowired
	private PictureService service;

	@PostMapping("/like-picture")
	public ResponseEntity<String> setLike(@RequestBody String imageId,
										  @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
		try{
			service.setLike(Long.parseLong(imageId), service.getUsername(token));
			return new ResponseEntity<>("Like set successfuly", HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>("Like set failed, reason: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("unlike-picture")
	public ResponseEntity<String> unsetLike(@RequestBody String imageId,
											@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token){
		try{
			service.deleteLike(Long.parseLong(imageId), service.getUsername(token));
			return new ResponseEntity<>("Like deleted successfuly", HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>("Like delete failed, reason: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/add-category")
	public ResponseEntity<?> addCategory(@Valid CategoryDTO category,BindingResult bindingResult,
											  @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token){
		if (bindingResult.hasErrors()) {
			List<String> errors = new ArrayList<>();
			for (ObjectError error : bindingResult.getAllErrors()) {
				errors.add(error.getDefaultMessage());
			}
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		try{
			service.addCategory(category, service.getUsername(token));
			return new ResponseEntity<>("Category added successfuly", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Category added failed, reason: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@DeleteMapping("/delete-category")
	public ResponseEntity<String> deleteCategory(String category,
												 @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token){
		try{
			service.deleteCategory(category, service.getUsername(token));
			return new ResponseEntity<>("Category deleted successfuly", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Category deleted, reason: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/upload-picture")
	public ResponseEntity<?> uploadPicture(@RequestBody @Valid PictureDTO pictureDTO, BindingResult bindingResult,
									@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
		if (bindingResult.hasErrors()) {
			List<String> errors = new ArrayList<>();
			for (ObjectError error : bindingResult.getAllErrors()) {
				errors.add(error.getDefaultMessage());
			}
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		try{
			service.savePicture(pictureDTO, service.getUsername(token));
			return new ResponseEntity<>("Picture uploaded successfully", HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>("Picture uploaded failed, reason: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/get-likes-count")
	public String getLikes(@RequestParam String imageId) {
		long count = service.getLikesCount(Long.parseLong(imageId));
		return Long.toString(count);
	}

	@DeleteMapping("delete-picture")
	public ResponseEntity<String> deletePicture(@RequestParam String imageId,
												@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
		try{
			service.deletePicture(Long.parseLong(imageId), service.getUsername(token));
		} catch (Exception e){
			return new ResponseEntity<>("Delete picture failed, reason: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Picture uploaded successfully", HttpStatus.OK);
	}
}
