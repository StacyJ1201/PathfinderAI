package com.synergisticit.PathfinderAI_Gateway.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ChatResponse {

    private String message;
    private List<Hotel> suggestedHotels;
    private LocalDateTime timestamp;
}
