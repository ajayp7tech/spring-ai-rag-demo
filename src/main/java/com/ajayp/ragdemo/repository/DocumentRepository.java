package com.ajayp.ragdemo.repository;
import com.ajayp.ragdemo.model.DocumentMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DocumentRepository extends JpaRepository<DocumentMetadata, String> {}