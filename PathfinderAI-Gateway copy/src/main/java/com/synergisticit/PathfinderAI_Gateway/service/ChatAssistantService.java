package com.synergisticit.PathfinderAI_Gateway.service;

import com.synergisticit.PathfinderAI_Gateway.dto.HotelSearchCriteria;
import com.synergisticit.PathfinderAI_Gateway.model.ChatMessage;

import java.util.List;

public interface ChatAssistantService {
    String getAIResponse(String prompt);
    HotelSearchCriteria extractSearchCriteria(String userMessage);
    String processUserMessage(String jwtToken, String userMessage);

}
