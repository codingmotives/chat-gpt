package com.example.chatgpt.gateway.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class PromptMessage {

    String role;
    String content;
}
