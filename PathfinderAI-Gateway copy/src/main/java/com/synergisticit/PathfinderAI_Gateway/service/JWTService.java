package com.synergisticit.PathfinderAI_Gateway.service;

public interface JWTService {
    String generateToken(String sessionId);
    String validateAndGetSessionId(String token);
}
