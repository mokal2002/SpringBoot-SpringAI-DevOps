package com.mokal.learn_spring_ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LearnSpringAiApplication {

	public static void main(String[] args) {
		System.setProperty("user.timezone", "Asia/Kolkata");
		SpringApplication.run(LearnSpringAiApplication.class, args);
	}

}
