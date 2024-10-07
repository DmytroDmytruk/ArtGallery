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
public class CategoryDTO {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @ValidateImageData
    private byte[] image;
}
