package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.PictureDTO;
import com.example.demo.service.CatalogService;
import com.example.demo.entity.*;
import com.example.demo.repository.ImageRepository;


@Configuration(proxyBeanMethods = false)
class RestTemplateConfiguration { 
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}

@RestController
@RequestMapping("/catalog")
public class CatalogController {
	@Autowired
	private CatalogService service;
	@Autowired
	ImageRepository repository;
	
	@GetMapping("/pictures")
	public ResponseEntity<List<PictureDTO>> getPictures(@RequestParam(defaultValue = "") String dateFrom,
														@RequestParam(defaultValue = "") String dateTo,
														@RequestParam(defaultValue = "0") Integer likesFrom,
														@RequestParam(defaultValue = "30000") Integer likesTo,
														@RequestParam(defaultValue = "date") String sortParam,
														@RequestParam(defaultValue = "asc") String sortOrder,
														@RequestParam(defaultValue = "") String searchString,
														@RequestParam(defaultValue = "0") Integer page,
														@RequestParam(defaultValue = "10") Integer count) {
		List<PictureDTO> images;
		try {
			images = service.getAllSortedAndFilteredPictures(dateFrom, dateTo, likesFrom, likesTo, sortParam, sortOrder, searchString, page, count);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(images, HttpStatus.OK);
	}


	@GetMapping("get-all-categories")
	public ResponseEntity<List<CategoryDTO>> getAllCategories(){
		List<CategoryDTO> result = service.getAllCategories();  
		return new ResponseEntity<List<CategoryDTO>>(result, HttpStatus.OK);
	}
		
	@GetMapping("/get-pictures-all")
	public ResponseEntity<List<PictureDTO>> getAllPictures(@RequestParam String page, @RequestParam String size) {
		List<PictureDTO> images = service.getAllPictures(Integer.parseInt(page) , Integer.parseInt(size));
		List<Image> image = repository.findAll();
		PictureDTO pictureDTO = new PictureDTO();
		pictureDTO.setImageData(image.get(0).getImageData());
		return new ResponseEntity<>(images, HttpStatus.OK);
	}
	
	@GetMapping("/get-pictures-by-user")
	public ResponseEntity<List<PictureDTO>> getAllPicturesByUser(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
		List<PictureDTO> images = service.getPicturesByUser(token);
		List<Image> image = repository.findAll();
		PictureDTO pictureDTO = new PictureDTO();
		pictureDTO.setImageData(image.get(0).getImageData());
		return new ResponseEntity<>(images, HttpStatus.OK);
	}
}
