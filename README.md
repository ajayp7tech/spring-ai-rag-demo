# Spring AI — RAG Pipeline Demo

<p align="left">
  <img src="https://img.shields.io/badge/Java_21-ED8B00?style=flat-square&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring_Boot_3.x-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring_AI-6DB33F?style=flat-square&logo=spring&logoColor=white"/>
  <img src="https://img.shields.io/badge/OpenAI-412991?style=flat-square&logo=openai&logoColor=white"/>
  <img src="https://img.shields.io/badge/pgvector-4169E1?style=flat-square&logo=postgresql&logoColor=white"/>
  <img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white"/>
  <img src="https://img.shields.io/badge/License-MIT-green?style=flat-square"/>
</p>

A production-style **Retrieval-Augmented Generation (RAG)** pipeline built with Spring AI, Java 21, and OpenAI. Upload documents, ask questions in plain English, and get accurate answers grounded in your own content — not hallucinated from the model's training data.

Demonstrates how to integrate Large Language Models (LLMs) into a standard Spring Boot backend using Spring AI's abstraction layer — swappable between OpenAI, Anthropic, and local models with zero application code changes.

---

## 📐 How RAG works

```
                         INGESTION PIPELINE
┌──────────────────────────────────────────────────────────────┐
│                                                              │
│  Upload Document (PDF / TXT / DOCX)                         │
│         │                                                    │
│         ▼                                                    │
│  [Document Loader] ──▶ [Text Splitter] ──▶ [Embedding Model]│
│                         chunk into           convert text    │
│                         paragraphs           to vectors      │
│                              │                               │
│                              ▼                               │
│                     [pgvector Database]                      │
│                     store vector embeddings                  │
└──────────────────────────────────────────────────────────────┘

                          QUERY PIPELINE
┌──────────────────────────────────────────────────────────────┐
│                                                              │
│  User asks: "What is the claims appeal process?"            │
│         │                                                    │
│         ▼                                                    │
│  [Embed question] ──▶ [Vector Similarity Search]            │
│   convert to             find top-k most relevant           │
│   vector                 document chunks                    │
│         │                        │                          │
│         └────────────┬───────────┘                          │
│                      ▼                                       │
│             [Prompt Builder]                                 │
│             "Answer using this context: {chunks}            │
│              Question: {user question}"                     │
│                      │                                       │
│                      ▼                                       │
│             [OpenAI GPT-4o]                                  │
│             generates grounded answer                        │
│                      │                                       │
│                      ▼                                       │
│             Answer returned to user ✅                       │
└──────────────────────────────────────────────────────────────┘
```

---

## ✨ Features

- **Document ingestion** — upload PDF, TXT, or plain text; automatically chunked, embedded, and stored in pgvector
- **Semantic search** — cosine similarity search across stored embeddings to find the most relevant context
- **RAG-powered Q&A** — answers grounded in uploaded documents, not model hallucinations
- **Conversation memory** — multi-turn chat with context window management so follow-up questions work naturally
- **Model-agnostic design** — swap OpenAI for Anthropic Claude or a local Ollama model by changing one config property
- **REST API** — clean endpoints for document upload, question answering, and conversation history
- **Swagger UI** — interactive API docs at `/swagger-ui.html`
- **Docker Compose** — full local setup with PostgreSQL + pgvector extension pre-configured

---

## 🛠 Tech stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.x, Spring AI 1.x |
| LLM Provider | OpenAI GPT-4o (swappable to Anthropic / Ollama) |
| Embeddings | OpenAI text-embedding-3-small |
| Vector Store | PostgreSQL + pgvector extension |
| Document Parsing | Spring AI Document Readers (PDF, TXT) |
| API Docs | Springdoc OpenAPI 3 (Swagger UI) |
| Testing | JUnit 5, Mockito, Testcontainers |
| Build | Maven |
| Containers | Docker, Docker Compose |
| CI/CD | GitHub Actions |

---

## 📁 Project structure

```
spring-ai-rag-demo/
├── .github/
│   └── workflows/
│       └── ci.yml
├── src/
│   ├── main/
│   │   ├── java/com/ajayp/ragdemo/
│   │   │   ├── RagDemoApplication.java
│   │   │   ├── config/
│   │   │   │   ├── VectorStoreConfig.java       # pgvector setup
│   │   │   │   ├── AiModelConfig.java           # OpenAI / model config
│   │   │   │   └── OpenApiConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── DocumentController.java      # Upload endpoints
│   │   │   │   └── ChatController.java          # Q&A endpoints
│   │   │   ├── service/
│   │   │   │   ├── DocumentIngestionService.java # Chunk + embed + store
│   │   │   │   ├── RagQueryService.java          # Retrieve + augment + generate
│   │   │   │   └── ConversationService.java      # Memory management
│   │   │   ├── model/
│   │   │   │   ├── ChatRequest.java             # Java 21 Record
│   │   │   │   ├── ChatResponse.java            # Java 21 Record
│   │   │   │   └── DocumentMetadata.java        # Java 21 Record
│   │   │   └── exception/
│   │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── application-local.yml
│   └── test/
│       └── java/com/ajayp/ragdemo/
│           ├── service/
│           │   ├── DocumentIngestionServiceTest.java
│           │   └── RagQueryServiceTest.java
│           └── integration/
│               └── RagPipelineIntegrationTest.java
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md
```

---

## 🚀 Getting started

### Prerequisites

- Java 21+
- Docker & Docker Compose
- Maven 3.8+
- OpenAI API key — get one at [platform.openai.com](https://platform.openai.com)

### Setup

```bash
# Clone the repo
git clone https://github.com/ajayp7tech/spring-ai-rag-demo.git
cd spring-ai-rag-demo

# Add your OpenAI API key
export OPENAI_API_KEY=sk-your-key-here

# Start PostgreSQL with pgvector
docker-compose up -d postgres

# Run the application
mvn spring-boot:run

# API is live at:
http://localhost:8080

# Swagger UI:
http://localhost:8080/swagger-ui.html
```

---

## 📡 API endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/v1/documents/upload` | Upload and ingest a document |
| `GET` | `/api/v1/documents` | List all ingested documents |
| `DELETE` | `/api/v1/documents/{id}` | Remove a document and its embeddings |
| `POST` | `/api/v1/chat/ask` | Ask a question (RAG-powered) |
| `POST` | `/api/v1/chat/conversation` | Multi-turn conversation with memory |
| `DELETE` | `/api/v1/chat/conversation/{id}` | Clear conversation history |
| `GET` | `/actuator/health` | Health check |

### Upload a document

```bash
curl -X POST http://localhost:8080/api/v1/documents/upload \
  -H "Content-Type: multipart/form-data" \
  -F "file=@healthcare-policy.pdf" \
  -F "title=Healthcare Policy 2025"
```

```json
{
  "documentId": "DOC-0042",
  "title": "Healthcare Policy 2025",
  "chunks": 38,
  "status": "INGESTED",
  "ingestedAt": "2025-04-10T14:00:00Z"
}
```

### Ask a question

```json
POST /api/v1/chat/ask
Content-Type: application/json

{
  "question": "What is the process for appealing a denied claim?",
  "documentId": "DOC-0042"
}
```

```json
{
  "answer": "Based on the uploaded policy document, the claims appeal process involves three steps: (1) Submit a written appeal within 60 days of denial, (2) Include supporting medical documentation from your provider, (3) A decision will be issued within 30 days. For urgent cases, an expedited review can be requested...",
  "sourceChunks": [
    { "page": 12, "excerpt": "Members may appeal any adverse benefit determination..." },
    { "page": 14, "excerpt": "Expedited appeals must be decided within 72 hours..." }
  ],
  "model": "gpt-4o",
  "tokensUsed": 847
}
```

---

## ⚙️ Model configuration

Switching LLM providers requires only a config change — no application code changes:

```yaml
# application.yml

# OpenAI (default)
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        model: gpt-4o
      embedding:
        model: text-embedding-3-small

# Switch to Anthropic Claude
# spring:
#   ai:
#     anthropic:
#       api-key: ${ANTHROPIC_API_KEY}
#       chat:
#         model: claude-sonnet-4-5

# Switch to local Ollama (no API key needed)
# spring:
#   ai:
#     ollama:
#       base-url: http://localhost:11434
#       chat:
#         model: llama3
```

---

## 🔒 Security notes

- OpenAI API key loaded from environment variable — never hardcoded or committed
- `.env` file included in `.gitignore`
- No user data sent to OpenAI beyond the text chunks uploaded by the user
- pgvector stores only embeddings (float arrays) — original document text stored separately in PostgreSQL

---

## 📊 Test coverage

| Layer | Coverage |
|---|---|
| Ingestion service | 89% |
| Query service | 86% |
| Controllers | 84% |
| Overall | **87%** |

---

## 🗺 Roadmap

- [x] PDF and TXT document ingestion
- [x] pgvector semantic search
- [x] RAG-powered Q&A REST API
- [x] Multi-turn conversation memory
- [x] Model-agnostic configuration
- [ ] Streaming responses (Server-Sent Events)
- [ ] Multi-document cross-search
- [ ] LangChain4j integration comparison branch
- [ ] Angular frontend for document upload and chat UI
- [ ] Evaluation framework for answer accuracy (RAGAS)

---

## 👤 Author

**Ajay Pingali** — Senior Java Developer

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=flat-square&logo=linkedin&logoColor=white)](https://linkedin.com/in/ajayp7tech)
[![Portfolio](https://img.shields.io/badge/Portfolio-0f3460?style=flat-square&logo=github&logoColor=white)](https://ajayp7tech.github.io)

---

## 📄 License

This project is licensed under the MIT License — see [LICENSE](LICENSE) for details.
