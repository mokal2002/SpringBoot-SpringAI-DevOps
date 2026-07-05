package com.mokal.learn_spring_ai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AIServiceTests {

    @Autowired
    private AIService aiService;

    @Test
    void getJokeTest() {
        String cat = aiService.getJoke("Cat");
        System.out.println(cat);
    }
}
