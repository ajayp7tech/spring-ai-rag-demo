-- Enable pgvector extension
CREATE EXTENSION IF NOT EXISTS vector;

-- Vector embeddings table (managed by Spring AI)
CREATE TABLE IF NOT EXISTS vector_store (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    content     TEXT NOT NULL,
    metadata    JSONB,
    embedding   vector(1536)
);

-- Index for fast cosine similarity search
CREATE INDEX IF NOT EXISTS vector_store_embedding_idx
    ON vector_store
    USING ivfflat (embedding vector_cosine_ops)
    WITH (lists = 100);
