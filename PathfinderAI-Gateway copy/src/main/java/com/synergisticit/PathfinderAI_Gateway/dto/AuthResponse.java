package com.synergisticit.PathfinderAI_Gateway.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthResponse {
    private String token;
    private String sessionId;
    private LocalDateTime expiresAt;
}
