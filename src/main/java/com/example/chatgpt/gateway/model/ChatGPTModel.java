package com.example.chatgpt.gateway.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Setter
@Getter
public class ChatGPTModel {

    String model;
    double temperature;

    List<PromptMessage> messages;
}
