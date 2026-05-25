package com.ajayp.ragdemo.controller;
import com.ajayp.ragdemo.model.ChatRequest;
import com.ajayp.ragdemo.model.ChatResponse;
import com.ajayp.ragdemo.service.RagQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/chat") @RequiredArgsConstructor
public class ChatController {
    private final RagQueryService ragQueryService;
    @PostMapping("/ask")
    public ResponseEntity<ChatResponse> ask(@Valid @RequestBody ChatRequest request) {
        return ResponseEntity.ok(ragQueryService.answer(request.question(), request.documentId()));
    }
}