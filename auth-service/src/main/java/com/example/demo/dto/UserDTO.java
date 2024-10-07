package com.example.demo.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
public class UserDTO {
	private String id;
	private String email;
	private byte[] avatar;
	private String username;
}
