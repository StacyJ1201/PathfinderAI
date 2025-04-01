package com.synergisticit.PathfinderAI_Gateway.repository;

import com.synergisticit.PathfinderAI_Gateway.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Optional<ChatMessage> findFirstBySessionIdOrderByTimestampDesc(String sessionId);
    List<ChatMessage> findBySessionIdOrderByTimestampAsc(String sessionId);
}
