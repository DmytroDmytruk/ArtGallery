package com.example.demo.controller.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import feign.Headers;
import feign.Param;
import jakarta.ws.rs.HeaderParam;

@FeignClient(name = "picture-service")
public interface PictureProxy {

    @GetMapping("/picture/get-likes-count")
    String getLikes(@RequestParam("imageId") String imageId);
}