package com.mokal.learn_spring_ai.service;

import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AIServiceTests {

    @Autowired
    private AIService aiService;


    @Test
    void testAskAI() {
        String cat = aiService.askAI("Spring Boot is a Java framework used to build production-ready applications quickly");
        System.out.println(cat);
    }

    @Test
    void getJokeTest() {
        String cat = aiService.getJoke("Cat");
        System.out.println(cat);
    }

    @Test
    void getEmbeddingTest() {
        var embeddingModel = aiService.getEmbedding("Cat");
        System.out.println(embeddingModel);
        for (float e : embeddingModel) {
            System.out.println(e + " ");
        }
    }

    @Test
    void getDataToVector() {
        aiService.ingestDatatoVectorStore();
    }

    @Test
    void similaritySearchTest() {
        var thisIsATest = aiService.similaritySearch("This is a test");
        System.out.println(thisIsATest);
    }


}
