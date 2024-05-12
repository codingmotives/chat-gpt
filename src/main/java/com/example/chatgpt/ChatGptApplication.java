package com.example.chatgpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.example.chatgpt"})
public class ChatGptApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatGptApplication.class, args);
	}
}
