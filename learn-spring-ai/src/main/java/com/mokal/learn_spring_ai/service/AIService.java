package com.mokal.learn_spring_ai.service;


import com.mokal.learn_spring_ai.dto.Joke;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatClient chatClient;


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

}
