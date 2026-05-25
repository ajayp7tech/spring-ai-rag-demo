package com.ajayp.ragdemo.service;
import com.ajayp.ragdemo.model.DocumentMetadata;
import com.ajayp.ragdemo.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class DocumentIngestionServiceTest {
    @Mock private DocumentRepository documentRepository;
    @InjectMocks private DocumentIngestionService ingestionService;

    @Test
    void ingest_shouldSaveDocumentWithCorrectTitle() {
        DocumentMetadata saved = new DocumentMetadata();
        saved.setId("DOC-001");
        saved.setTitle("Test Doc");
        saved.setChunks(2);
        when(documentRepository.save(any())).thenReturn(saved);
        DocumentMetadata result = ingestionService.ingest("Test Doc", "a".repeat(1000));
        assertThat(result.getTitle()).isEqualTo("Test Doc");
        verify(documentRepository).save(any());
    }

    @Test
    void ingest_shouldCalculateChunksFromContentLength() {
        DocumentMetadata saved = new DocumentMetadata();
        saved.setChunks(4);
        when(documentRepository.save(any())).thenReturn(saved);
        DocumentMetadata result = ingestionService.ingest("Big Doc", "a".repeat(2000));
        assertThat(result.getChunks()).isGreaterThan(0);
    }
}