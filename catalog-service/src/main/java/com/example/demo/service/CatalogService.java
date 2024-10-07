package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.controller.proxy.AuthProxy;
import com.example.demo.controller.proxy.PictureProxy;
import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.PictureDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Image;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.UserRepository;



@Service
public class CatalogService {
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private PictureProxy pictureProxy;
	
	@Autowired
	private AuthProxy authProxy;
	
	public Optional<Image> getPicture(long id){
		return imageRepository.findById(id);
	}
	
	public List<PictureDTO> getAllPictures(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
        try {
        	List<Image> images = imageRepository.findAll(pageable).toList();
			return images.stream()
                    .map(image -> PictureDTO.builder()
                            .imageData(image.getImageData())
                            .likesCount(pictureProxy.getLikes(image.getId().toString()))
                            .name(image.getName())
                            .description(image.getDescription())
                            .authorName(image.getUser().getUsername())
                            .categories(image.getCategories().stream()
                                    .map(Category::getName)
                                    .collect(Collectors.toList()))
                            .uploadDate(image.getUploadDate())
                            .build())
                    .collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
	}
    
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(category -> {
                    byte[] illustrationData = category.getIllustrationData();
                    return new CategoryDTO(category.getName(), illustrationData);
                })
                .collect(Collectors.toList());
    }

	/*public List<PictureDTO> getLikedPictures(String token) {
		User user = userRepository.findByUsername(authProxy.getUsername(token)).orElse(null);
		List<Likes> userLikes = user.getLikes();
        List<PictureDTO> resultImages = new ArrayList<>();
        for (Likes like : userLikes) {
        	resultImages.add(new PictureDTO(like.getImage(),
        			pictureProxy.getLikes(like.getImage().getId().toString())));
        }
		return resultImages;
	}*/


    /*public List<PictureDTO> getAllSortedAndFilteredPictures(
            Integer likesFrom, Integer likesTo, Date dateFrom, Date dateTo,
            String searchString, Integer page, Integer countOfElementsInPage,
            String sortOrder, String sortParam, String toke){


		Pageable pageable = PageRequest.of(page - 1, countOfElementsInPage);
		return null;
    }*/

/*
* @RequestParam(defaultValue = "") String dateFrom,
@RequestParam(defaultValue = "") String dateTo,
@RequestParam(defaultValue = "0") Integer likesFrom,
@RequestParam(defaultValue = "30000") Integer likesTo,
@RequestParam(defaultValue = "date") String sortParam,
@RequestParam(defaultValue = "asc") String sortOrder,
@RequestParam(defaultValue = "") String searchString,
@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "10") Integer count
* */


    public List<PictureDTO> getAllSortedAndFilteredPictures(
            String dateFrom, String dateTo, Integer likesFrom, Integer likesTo,
            String sortParam, String sortOrder, String searchString,
            Integer page, Integer countOfElementsInPage) {

        Specification<Image> spec = Specification.where(null);

        Sort sort = null;
        if(sortOrder != null && sortOrder.equalsIgnoreCase("desc")){
            sort = Sort.by(Sort.Direction.DESC, sortParam);
            if (sortParam != null) {
                sort = Sort.by(Sort.Direction.DESC, sortParam);
            } else {
                sort = Sort.by(Sort.Direction.ASC, "defaultSortField");
            }
        } else {
            sort = Sort.by(Sort.Direction.ASC, sortParam);
        }
        Pageable pageable = PageRequest.of(page - 1, countOfElementsInPage, sort);
        if (likesFrom != null && likesTo != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.between(root.get("likesCount"), likesFrom, likesTo));
        }

        if (dateFrom != null && dateTo != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.between(root.get("uploadDate"), dateFrom, dateTo));
        }

        if (searchString != null && !searchString.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchString.toLowerCase() + "%"));
        }
        List<Image> images = imageRepository.findAll(spec, pageable).getContent();
        
        for (Image image : images) {
            String imageId = String.valueOf(image.getId());
            String likesCount = pictureProxy.getLikes(imageId);
            image.setLikesCount(Integer.parseInt(likesCount));
        }
        return images.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    private PictureDTO convertToDTO(Image image) {
        return PictureDTO.builder()
                .imageData(image.getImageData())
                .likesCount(pictureProxy.getLikes(image.getId().toString()))
                .name(image.getName())
                .description(image.getDescription())
                .authorName(image.getUser().getUsername())
                .categories(image.getCategories().stream()
                        .map(Category::getName)
                        .collect(Collectors.toList()))
                .uploadDate(image.getUploadDate())
                .id(image.getId())
                .build();
    }

	public List<PictureDTO> getPicturesByUser(String token) {
		User user = userRepository.findByUsername(authProxy.getUsername(token)).orElse(null);
        List<Image> userImages = imageRepository.findByUser(user);
        List<PictureDTO> pictureDTOList = new ArrayList<>();

        for (Image image : userImages) {
            PictureDTO pictureDTO = convertToDTO(image);
            pictureDTO.setLikesCount(pictureProxy.getLikes(image.getId().toString()));
            pictureDTOList.add(pictureDTO);
        }
        return pictureDTOList;
	}   
}		
		






