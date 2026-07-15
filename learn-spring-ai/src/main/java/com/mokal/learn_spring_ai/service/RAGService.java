package com.mokal.learn_spring_ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RAGService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Value("classpath:FinalScienticAmericanArticle.pdf")
    Resource pdffile;


    public void ingestPdfToVectorStore() {
        PagePdfDocumentReader reader = new PagePdfDocumentReader(pdffile);
        List<Document> pages = reader.get();

        TokenTextSplitter tokenTextSplitter = TokenTextSplitter.builder()
                .withChunkSize(200)
                .build();

        List<Document> chunks = tokenTextSplitter.apply(pages);
        List<Document> sanitized = chunks.stream()
                .map(doc -> new Document(
                        doc.getId(),
                        doc.getText().replace("\u0000", ""),
                        doc.getMetadata()
                ))
                .toList();

        vectorStore.add(sanitized);
    }

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
                        .topK(4)
                        .similarityThreshold(0.4)
                        .filterExpression("file_name == 'FinalScienticAmericanArticle.pdf'")
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
}
