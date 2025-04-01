package com.synergisticit.PathfinderAI_Gateway.controller;

import com.synergisticit.PathfinderAI_Gateway.dto.AuthResponse;
import com.synergisticit.PathfinderAI_Gateway.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JWTService jwtService;

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> getToken(){

        String sessionId = UUID.randomUUID().toString();

        String token = jwtService.generateToken(sessionId);

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setSessionId(sessionId);
        response.setExpiresAt(LocalDateTime.now().plusHours(1));

        return ResponseEntity.ok(response);
    }
}
