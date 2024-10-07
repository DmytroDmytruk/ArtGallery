package com.example.demo.controller;


import com.example.demo.controller.requests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.demo.controller.requests.AuthRequest;
import com.example.demo.dto.UserDTO;
import com.example.demo.entities.User;
import com.example.demo.service.AuthService;
import com.example.demo.service.JwtService;

import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody RegisterRequest request) {
        String token;
        try{
            token = service.saveUser(request);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<String> generateToken(@RequestBody AuthRequest authRequest) {
    	String token;
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            token = service.generateToken(authRequest.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Something went wrong, I don`t know what", HttpStatus.UNAUTHORIZED);
        }
    }
    
    @GetMapping("/token")
    public String getToken(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
		return token;
    	
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
        try {
            service.validateToken(token);
            return new ResponseEntity<>("Token is valid", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("token is not valid", HttpStatus.UNAUTHORIZED);
        }
    }
   
    @GetMapping("/user-details")
    public ResponseEntity<?> getUserInfo(
    		@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token){
        try{
            String name = service.getUsername(token);
            UserDTO resultDto = service.getUserInfo(name);
            return new ResponseEntity<>(resultDto, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    
    @GetMapping("/username")
    public ResponseEntity<String> getUsername(@RequestParam("token") String token){
        try {
           return new ResponseEntity<>(service.getUsername(token), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }
}
