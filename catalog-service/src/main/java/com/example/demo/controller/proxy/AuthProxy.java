package com.example.demo.controller.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(path = "/auth", name = "auth-service")
public interface AuthProxy {
	@GetMapping("/username")
	public String getUsername(@RequestParam("token") String token);
}

