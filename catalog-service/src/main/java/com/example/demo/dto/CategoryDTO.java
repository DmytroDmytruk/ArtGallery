package com.example.demo.dto;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
public class CategoryDTO {
	private String categoryName;
	private byte[] imageData;
}
