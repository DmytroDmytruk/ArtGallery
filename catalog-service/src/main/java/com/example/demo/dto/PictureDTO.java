package com.example.demo.dto;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
public class PictureDTO {
    private String name;
    private String description;
    private String category;
    private Long id;
    private Date uploadDate;
    private String authorName;
    private byte[] imageData;
    private String likesCount;
    private List<String> categories;

}

