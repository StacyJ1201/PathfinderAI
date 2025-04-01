package com.synergisticit.PathfinderAI_Gateway.repository;

import com.synergisticit.PathfinderAI_Gateway.model.ChatVector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatVectorRepository extends JpaRepository<ChatVector, Long> {
    @Query(value =
            "SELECT * FROM chat_vectors " +
                    "WHERE embedding IS NOT NULL " +
                    "ORDER BY embedding <-> :embedding " +
                    "LIMIT :limit",
            nativeQuery = true)
    List<ChatVector> findSimilarMessages(@Param("embedding") float[] embedding, @Param("limit") int limit);
}
