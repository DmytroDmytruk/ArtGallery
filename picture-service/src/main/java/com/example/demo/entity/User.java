package com.example.demo.entity;

import java.util.List;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String username;
	@Column
	private String password;
	@Column
	private String email;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column(columnDefinition="LONGBLOB")
	@Lob
	private byte[] avatar;
	@OneToMany(mappedBy = "user")
	private List<Likes> likes;
	@OneToMany(mappedBy = "user")
	private List<Image> images;
}

