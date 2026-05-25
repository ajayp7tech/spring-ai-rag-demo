package com.ajayp.ragdemo.model;
public record ChatResponse(String answer, String model, int tokensUsed) {}