package com.ajayp.ragdemo.controller;
import com.ajayp.ragdemo.model.DocumentMetadata;
import com.ajayp.ragdemo.service.DocumentIngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/documents") @RequiredArgsConstructor
public class DocumentController {
    private final DocumentIngestionService ingestionService;
    @PostMapping
    public ResponseEntity<DocumentMetadata> ingest(@RequestParam String title, @RequestBody String content) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingestionService.ingest(title, content));
    }
    @GetMapping
    public ResponseEntity<List<DocumentMetadata>> list() { return ResponseEntity.ok(ingestionService.listDocuments()); }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        ingestionService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}