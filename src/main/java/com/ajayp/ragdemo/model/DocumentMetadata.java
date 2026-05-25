package com.ajayp.ragdemo.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
@Entity @Table(name = "documents") @Data
public class DocumentMetadata {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private String id;
    @Column(nullable = false) private String title;
    private int chunks;
    private String status = "INGESTED";
    private LocalDateTime ingestedAt = LocalDateTime.now();
}