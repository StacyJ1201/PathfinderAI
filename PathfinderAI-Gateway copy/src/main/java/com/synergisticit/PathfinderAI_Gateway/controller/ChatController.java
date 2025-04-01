package com.synergisticit.PathfinderAI_Gateway.controller;

import com.synergisticit.PathfinderAI_Gateway.dto.ChatRequest;
import com.synergisticit.PathfinderAI_Gateway.dto.ChatResponse;
import com.synergisticit.PathfinderAI_Gateway.service.ChatAssistantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatAssistantService chatAssistantService;

    @PostMapping("/message")
    public ResponseEntity<ChatResponse> processMessage(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ChatRequest request){

        String token = authHeader.substring(7);

        String aiResponse = chatAssistantService.processUserMessage(token, request.getMessage());

        ChatResponse response = new ChatResponse();
        response.setMessage(aiResponse);
        response.setTimestamp(LocalDateTime.now());

        return ResponseEntity.ok(response);
    }
}
