package com.example.demo.dto;

import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
public class PicturesCatalogRequest {
    private String dateFrom;
    private String dateTo;
    private Integer likesFrom;
    private Integer likesTo;
    private String sortParam;
    private String sortOrder;
    private String searchString;
    private Integer page;
    private Integer count;
}
