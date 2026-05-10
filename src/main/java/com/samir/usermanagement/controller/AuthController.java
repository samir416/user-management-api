package com.samir.usermanagement.controller;

import com.samir.usermanagement.dto.LoginRequest;
import com.samir.usermanagement.jwt.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest request
    ) {

        // Check Username & Password
        if (
                request.getUsername().equals("samir")
                &&
                request.getPassword().equals("12345")
        ) {

            String token =
                    jwtUtil.generateToken(
                            request.getUsername()
                    );

            return ResponseEntity.ok(token);
        }

        return ResponseEntity
                .badRequest()
                .body("Invalid username or password");
    }
}