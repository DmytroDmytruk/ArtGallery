package com.example.demo.service;

import com.example.demo.controller.requests.RegisterRequest;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(RegisterRequest request) throws Exception {
        if(repository.existsByUsername(request.getUsername()) || repository.existsByUsername(request.getEmail())) {
            throw new Exception("Username is already in use");
        }
        User user = User.builder()
                .avatar(request.getAvatar())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        repository.save(user);
        return generateToken(request.getUsername());
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(JwtService.SECRET).parseClaimsJws(token).getBody().getSubject();
    }

	public UserDTO getUserInfo(String username) {
        return repository.findByUsername(username)
                .map(user -> new UserDTO(user.getId().toString(),
                        user.getEmail(),
                        user.getAvatar(),
                        user.getUsername()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
}
