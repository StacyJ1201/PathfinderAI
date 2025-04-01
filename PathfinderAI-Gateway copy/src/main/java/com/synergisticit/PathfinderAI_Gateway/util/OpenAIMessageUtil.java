package com.synergisticit.PathfinderAI_Gateway.util;


import com.theokanning.openai.completion.chat.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class OpenAIMessageUtil{

    public static ChatMessage createOpenAIMessage(String role, String current){
        return new ChatMessage(role, current);
    }

    public static ChatMessage convertToOpenAIMessage(com.synergisticit.PathfinderAI_Gateway.model.ChatMessage ourMessage){
        return new ChatMessage(ourMessage.getRole(), ourMessage.getContent());
    }

    public static List<ChatMessage> convertToOpenAIMessages(List<com.synergisticit.PathfinderAI_Gateway.model.ChatMessage> messages){
        List<ChatMessage> openAiMessages = new ArrayList<>();
        for(com.synergisticit.PathfinderAI_Gateway.model.ChatMessage message : messages){
            openAiMessages.add(convertToOpenAIMessage(message));
        }
        return openAiMessages;
    }
}
