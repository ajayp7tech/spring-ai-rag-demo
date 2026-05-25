package com.ajayp.ragdemo.service;
import com.ajayp.ragdemo.model.DocumentMetadata;
import com.ajayp.ragdemo.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
@Service @RequiredArgsConstructor @Slf4j
public class DocumentIngestionService {
    private final DocumentRepository documentRepository;
    public DocumentMetadata ingest(String title, String content) {
        int chunks = Math.max(1, content.length() / 500);
        DocumentMetadata doc = new DocumentMetadata();
        doc.setTitle(title);
        doc.setChunks(chunks);
        DocumentMetadata saved = documentRepository.save(doc);
        log.info("Ingested document: {} with {} chunks", title, chunks);
        return saved;
    }
    public List<DocumentMetadata> listDocuments() { return documentRepository.findAll(); }
    public void deleteDocument(String id) { documentRepository.deleteById(id); }
}