package com.mokal.learn_spring_ai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RAGServiceTests {

    @Autowired
    private RAGService ragService;


//    @Test
//    void ingestPdf() {
//        ragService.ingestPdfToVectorStore();
//    }

    @Test
    void testAskAI() {
        var response = ragService.askAI("tell me about blackhole intresting");
        System.out.println(response);
    }

    @Test
    void testAskAIWithAdvisor() {
        var response = ragService.askAIWithAdvisors("What is my Name?", "mokal29s");
        System.out.println(response);
    }
}
