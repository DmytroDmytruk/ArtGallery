package com.example.demo.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
public class GetPicturesRequest {
	private String pageData;
	private String countData;
	private int page;
	private int count;
	private String filterValue;
	private String sortParam;
	private String sortOrder;
	private String filterDate;
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format. Expected format is yyyy-MM-dd")
	private String dateFrom;
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format. Expected format is yyyy-MM-dd")
	private String dateTo;
	private String filterLikes;
	private String likesFrom;
	private String likesTo;
	private String searchString;
	private String filterCategory;
}
