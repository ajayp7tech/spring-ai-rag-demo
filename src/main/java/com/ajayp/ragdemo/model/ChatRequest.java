package com.ajayp.ragdemo.model;
import jakarta.validation.constraints.NotBlank;
public record ChatRequest(@NotBlank String question, String documentId) {}