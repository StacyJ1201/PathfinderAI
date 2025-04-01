package com.synergisticit.PathfinderAI_Gateway.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synergisticit.PathfinderAI_Gateway.dto.Hotel;
import com.synergisticit.PathfinderAI_Gateway.dto.HotelSearchCriteria;
import com.synergisticit.PathfinderAI_Gateway.model.ChatSession;
import com.synergisticit.PathfinderAI_Gateway.model.ChatVector;
import com.synergisticit.PathfinderAI_Gateway.repository.ChatMessageRepository;
import com.synergisticit.PathfinderAI_Gateway.repository.ChatSessionRepository;
import com.synergisticit.PathfinderAI_Gateway.repository.ChatVectorRepository;
import com.synergisticit.PathfinderAI_Gateway.util.OpenAIMessageUtil;
import com.synergisticit.PathfinderAI_Gateway.model.ChatMessage;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.embedding.EmbeddingRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;



@Service
@Transactional
public class ChatAssistantServiceImpl implements ChatAssistantService {

    @Autowired
    OpenAiService openAiService;

    @Autowired
    ChatMessageRepository chatMessageRepository;

    @Autowired
    ChatSessionRepository chatSessionRepository;

    @Autowired
    ChatVectorRepository chatVectorRepository;

    @Autowired
    HotelSearchService hotelSearchService;

    @Autowired
    JWTServiceImpl jwtService;

    private static final String SYSTEM_PROMPT = """
            You are Pathfinder, an AI travel assistant for a hotel booking platform. Your job is to:
            1. Help users find hotels that match their preferences
            2. Ask clarifying questions when needed
            3. Provide detailed, helpful information about hotels
            4. Maintain context throughout conversation
            
            Important details about hotels include
            - Location: (city, state)
            - Price range and discount
            - Star rating and amenities
            - Room types and availability
            
            Always be professional, friendly, and focus on understanding user needs.
            """;

    @Override
    public String processUserMessage(String jwtToken, String userMessage) {
        String sessionId = jwtService.validateAndGetSessionId(jwtToken);

        ChatSession chatSession = chatSessionRepository.findBySessionId(sessionId)
                .orElseGet(() -> createNewSession(sessionId));

        List<ChatVector> similarMessages = new ArrayList<>();

        HotelSearchCriteria criteria = extractSearchCriteria(userMessage);

        List<Hotel> matchingHotels = hotelSearchService.searchHotels(
                criteria.getLocation(),
                criteria.getMaxPrice(),
                criteria.getStarRating(),
                criteria.getAmenities()
        );

        String enhancedPrompt = buildPromptWithContext(userMessage, chatSession, similarMessages, matchingHotels);

        String aiResponse = getAIResponse(enhancedPrompt);

        saveMessage(sessionId, "user", userMessage);
        saveMessage(sessionId, "assistant", aiResponse);

        updateSessionContext(chatSession, userMessage, aiResponse);

        return aiResponse;
    }

    @Override
    public String getAIResponse(String prompt) {
        List<com.theokanning.openai.completion.chat.ChatMessage> messages = new ArrayList<>();
        messages.add(OpenAIMessageUtil.createOpenAIMessage("system", SYSTEM_PROMPT));
        messages.add(OpenAIMessageUtil.createOpenAIMessage("user", prompt));

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .model("gpt-4")
                .temperature(0.7)
                .maxTokens(500)
                .build();

        return openAiService.createChatCompletion(completionRequest)
                .getChoices().get(0).getMessage().getContent();
    }

    private ChatSession createNewSession(String sessionId){
        ChatSession chatSession = new ChatSession();
        chatSession.setSessionId(sessionId);
        chatSession.setCreatedAt(LocalDate.now());
        chatSession.setUpdatedAt(LocalDate.now());
        return chatSessionRepository.save(chatSession);
    }

    private void updateSessionContext(ChatSession session, String userMessage, String aiResponse) {
        String newContext = String.format("%s\nUser: %s\nAssistant: %s",
                session.getCurrentContext() != null ? session.getCurrentContext() : "",
                userMessage,
                aiResponse);
        session.setCurrentContext(newContext);
        session.setUpdatedAt(LocalDate.now());
        chatSessionRepository.save(session);
    }

    private String buildPromptWithContext(String userMessage, ChatSession session,
                                          List<ChatVector> similarMessages, List<Hotel> hotels) {
        StringBuilder prompt = new StringBuilder();

        if (session.getCurrentContext() != null) {
            prompt.append("Previous conversation:\n")
                    .append(session.getCurrentContext())
                    .append("\n\n");
        }

        if (!similarMessages.isEmpty()) {
            prompt.append("Similar past conversations:\n");
            similarMessages.forEach(msg ->
                    prompt.append("- ").append(msg.getContent()).append("\n")
            );
            prompt.append("\n");
        }

        prompt.append("Current user message: ").append(userMessage).append("\n\n");
        prompt.append("Available hotels:\n");
        for (Hotel hotel : hotels) {
            prompt.append(String.format("""
                - %s
                  City: %s, %s
                  Rating: %d stars
                  Price: $%.2f
                  Amenities: %s
                  
                """,
                    hotel.getHotelName(),
                    hotel.getCity(),
                    hotel.getState(),
                    hotel.getStarRating(),
                    hotel.getAveragePrice(),
                    String.join(", ", hotel.getAmenities())
            ));
        }
        return prompt.toString();
    }


    @Override
    public HotelSearchCriteria extractSearchCriteria(String userMessage) {
        String extractionPrompt = userMessage + " Extract hotel search criteria from the above message and respond in JSON format with these fields: " +
                "{ " +
                "\"location\": \"city name\", " +
                "\"checkIn\": \"YYYY-MM-DD\", " +
                "\"checkOut\": \"YYYY-MM-DD\", " +
                "\"guests\": number, " +
                "\"maxPrice\": number, " +
                "\"starRating\": number, " +
                "\"amenities\": [\"amenity1\", \"amenity2\"] " +
                "}";

        try {
            List<com.theokanning.openai.completion.chat.ChatMessage> messages = new ArrayList<>();
            messages.add(OpenAIMessageUtil.createOpenAIMessage("system",
                    "You are a helpful assistant that extracts hotel search criteria from user messages. " +
                            "Respond ONLY with the JSON format specified. If a field is not mentioned, omit it from the JSON."));
            messages.add(OpenAIMessageUtil.createOpenAIMessage("user", extractionPrompt));

            ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                    .messages(messages)
                    .model("gpt-4")
                    .temperature(0.0)
                    .maxTokens(300)
                    .build();

            String jsonResponse = openAiService.createChatCompletion(completionRequest)
                    .getChoices().get(0).getMessage().getContent();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(jsonResponse);

            HotelSearchCriteria criteria = new HotelSearchCriteria();

            if (node.has("location")) {
                criteria.setLocation(node.get("location").asText());
            }
            if (node.has("checkIn")) {
                criteria.setCheckIn(node.get("checkIn").asText());
            }
            if (node.has("checkOut")) {
                criteria.setCheckOut(node.get("checkOut").asText());
            }
            if (node.has("guests")) {
                criteria.setGuests(node.get("guests").asInt());
            }
            if (node.has("maxPrice")) {
                criteria.setMaxPrice(node.get("maxPrice").asDouble());
            }
            if (node.has("starRating")) {
                criteria.setStarRating(node.get("starRating").asInt());
            }
            if (node.has("amenities") && node.get("amenities").isArray()) {
                Set<String> amenities = new HashSet<>();
                node.get("amenities").forEach(amenity ->
                        amenities.add(amenity.asText())
                );
                criteria.setAmenities(amenities);
            }

            return criteria;

        } catch (Exception e) {
            return new HotelSearchCriteria();
        }
    }

    private void saveMessage(String sessionId, String role, String content){
        ChatMessage message = new ChatMessage();
        message.setSessionId(sessionId);
        message.setRole(role);
        message.setContent(content);
        chatMessageRepository.save(message);
    }
}
