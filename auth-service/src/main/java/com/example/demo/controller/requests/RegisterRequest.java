package com.example.demo.controller.requests;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private byte[] avatar;
}
