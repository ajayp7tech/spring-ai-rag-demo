package com.ajayp.ragdemo.service;
import com.ajayp.ragdemo.model.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Service @Slf4j
public class RagQueryService {
    public ChatResponse answer(String question, String documentId) {
        log.info("Processing RAG query: {} for document: {}", question, documentId);
        String answer = "This is a simulated RAG response for: " + question +
            ". In production, this would query the vector store and call the LLM API.";
        return new ChatResponse(answer, "gpt-4o-simulated", 150);
    }
}