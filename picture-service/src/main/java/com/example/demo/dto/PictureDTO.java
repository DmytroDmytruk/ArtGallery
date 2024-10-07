package com.example.demo.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PictureDTO {
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Description cannot be empty")
    private String description;
    @ValidateCategory()
    private String category;
    @NotEmpty(message = "ImageData cannot be empty")
    @ValidateImageData
    private byte[] imageData;
}
