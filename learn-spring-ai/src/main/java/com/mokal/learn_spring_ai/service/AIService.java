package com.mokal.learn_spring_ai.service;


import com.mokal.learn_spring_ai.dto.Joke;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

//    public String askAI(String prompt) {
//
//        String template = """
//                You are an AI assistant.
//
//                Use ONLY the following context to answer the user's question.
//
//                Rules:
//                - Do not use outside knowledge.
//                - If the answer is not found in the context, reply:
//                  "I don't have enough information in the provided documents."
//                - Keep the answer accurate and concise.
//                - Format the answer in Markdown when helpful.
//
//                Context:
//                {context}
//
//
//                Answer:
//                """;
//        List<Document> documents = vectorStore.similaritySearch(SearchRequest.builder()
//                .query(prompt)
//                .topK(2)
//                .filterExpression("category == 'java' or category == 'security").build());
//
//        String context = documents.stream()
//                .map(Document::getText)
//                .collect(Collectors.joining("\n\n"));
//
//        PromptTemplate promptTemplate = new PromptTemplate(template);
//        promptTemplate.render(Map.of("context", context));
//        return chatClient.prompt()
//                .user(prompt)
//                .call()
//                .content();
//    }

    public String askAI(String question) {

        String template = """
                You are an AI assistant.
                
                Use ONLY the following context to answer the user's question.
                
                Rules:
                - Do not use outside knowledge.
                - If the answer is not found in the context, reply:
                  "I don't have enough information in the provided documents."
                - Keep the answer accurate and concise.
                - Format the answer in Markdown when helpful.
                
                Context:
                {context}
                
                
                Answer:
                """;

        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(question)
                        .topK(2)
                        .similarityThreshold(0.5)
                        .filterExpression("category == 'java' or category == 'security'")
                        .build()
        );

        String context = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        PromptTemplate promptTemplate = new PromptTemplate(template);

        String systemPrompt = promptTemplate.render(Map.of(
                "context", context
        ));

        return chatClient.prompt()
                .system(systemPrompt)
                .user(question)
                .advisors(new SimpleLoggerAdvisor())
                .call()
                .content();
    }

    public float[] getEmbedding(String text) {
        return embeddingModel.embed(text);
    }

    public void ingestDatatoVectorStore(String text) {
        Document document = new Document(text);
        vectorStore.add(List.of(document));
    }

    public void ingestDatatoVectorStore() {
        vectorStore.add(springAiDocs());
    }

    public List<Document> similaritySearch(String text) {
        return vectorStore.similaritySearch(text);
    }

    public String getJoke(String topic) {
        String systemPrompt = """
                You a Cutiee joker, you make evry object in cute in 2 lines.
                You dont make jokes about anything only cats realated.
                Give Joke on a Topic : {topic}
                """;

        PromptTemplate promptTemplate = new PromptTemplate(systemPrompt);
        String renderText = promptTemplate.render(Map.of("topic", topic));

        var response = chatClient.prompt()
                .user(renderText)
                .advisors(
                        new SimpleLoggerAdvisor()
                )
                .call()
//                .chatClientResponse();
                .entity(Joke.class);

//        return response.chatResponse().getResult().getOutput().getText();
        return response.text();
    }

    public static List<Document> springAiDocs() {
        return List.of(

                new Document("""
                        Spring Boot is a Java framework used to build production-ready applications quickly.
                        It provides embedded servers, auto-configuration, and starter dependencies.
                        """,
                        Map.of(
                                "id", "DOC-001",
                                "category", "Java",
                                "source", "Knowledge Base"
                        )
                ),

                new Document("""
                        Hibernate is an ORM framework that maps Java objects to relational database tables.
                        It simplifies CRUD operations and supports JPQL, HQL, caching, and lazy loading.
                        """,
                        Map.of(
                                "id", "DOC-002",
                                "category", "Database",
                                "source", "Knowledge Base"
                        )
                ),

                new Document("""
                        Docker is a containerization platform that packages applications with their dependencies.
                        Containers are lightweight, portable, and consistent across environments.
                        """,
                        Map.of(
                                "id", "DOC-003",
                                "category", "DevOps",
                                "source", "Knowledge Base"
                        )
                ),

                new Document("""
                        Spring Security provides authentication and authorization for Java applications.
                        It supports JWT, OAuth2, LDAP, role-based access control, and custom login pages.
                        """,
                        Map.of(
                                "id", "DOC-004",
                                "category", "Security",
                                "source", "Knowledge Base"
                        )
                ),

                new Document("""
                        PostgreSQL is an open-source relational database known for reliability and performance.
                        It supports JSON, indexing, transactions, full-text search, and advanced SQL features.
                        """,
                        Map.of(
                                "id", "DOC-005",
                                "category", "Database",
                                "source", "Knowledge Base"
                        )
                )

        );
    }

}
