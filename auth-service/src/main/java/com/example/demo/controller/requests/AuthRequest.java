package com.example.demo.controller.requests;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
public class AuthRequest {
    private String username;
    private String password;
}
