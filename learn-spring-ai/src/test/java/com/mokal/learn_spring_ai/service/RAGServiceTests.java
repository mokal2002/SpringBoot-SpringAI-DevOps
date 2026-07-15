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
}
