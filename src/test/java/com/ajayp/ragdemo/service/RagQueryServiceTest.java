package com.ajayp.ragdemo.service;
import com.ajayp.ragdemo.model.ChatResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RagQueryServiceTest {
    @InjectMocks private RagQueryService ragQueryService;

    @Test
    void answer_shouldReturnNonNullResponse() {
        ChatResponse response = ragQueryService.answer("What is the claims process?", "DOC-001");
        assertThat(response).isNotNull();
        assertThat(response.answer()).isNotBlank();
        assertThat(response.tokensUsed()).isGreaterThan(0);
    }

    @Test
    void answer_shouldIncludeQuestionInResponse() {
        String question = "How do I file a claim?";
        ChatResponse response = ragQueryService.answer(question, null);
        assertThat(response.answer()).contains(question);
    }
}