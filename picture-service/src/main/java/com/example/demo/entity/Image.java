package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;



@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Lob
	@Column(name="image_data", columnDefinition="LONGBLOB")
	private byte[] imageData;
	private String description;
	@Transient
	private int likesCount;
	private Date uploadDate;
	@OneToMany(mappedBy = "image")
	private List<Likes> likes;
	@ManyToMany
	@JoinTable(
			name = "image_category",
			joinColumns = @JoinColumn(name = "image_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id")
	)
	private List<Category> categories;
	@ManyToOne
	private User user;
	@ManyToOne
	private Category category;
}