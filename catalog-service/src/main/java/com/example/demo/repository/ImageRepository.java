package com.example.demo.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entity.Image;
import com.example.demo.entity.User;

public interface ImageRepository extends JpaRepository<Image, Long>, JpaSpecificationExecutor<Image> {
	Page<Image> findAll(Specification<Image> specification, Pageable pageable);	
	Image findFirstByCategories_Name(String categoryName);
    List<Image> findByLikesCountBetweenAndUploadDateBetweenAndNameContainingIgnoreCase(
            Integer likesFrom, Integer likesTo, Date dateFrom, Date dateTo, String searchString, Pageable pageable);
    List<Image> findAllByOrderByNameAsc(Pageable pageable);
    List<Image> findAllByOrderByNameDesc(Pageable pageable);
    List<Image> findAllByOrderByUploadDateAsc(Pageable pageable);
    List<Image> findAllByOrderByUploadDateDesc(Pageable pageable);
    List<Image> findByUser(User user);
}
