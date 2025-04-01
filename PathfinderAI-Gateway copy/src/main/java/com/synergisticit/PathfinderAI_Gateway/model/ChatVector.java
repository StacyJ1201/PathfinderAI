package com.synergisticit.PathfinderAI_Gateway.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_vectors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatVector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "vector(1536)")
    private float[] embedding;

    @Column(columnDefinition = "text")
    private String content;

    @Column(nullable = false)
    private String sessionId;

    private LocalDateTime timestamp = LocalDateTime.now();

    public boolean hasEmbedding() {
        return embedding != null && embedding.length == 1536;
    }
}