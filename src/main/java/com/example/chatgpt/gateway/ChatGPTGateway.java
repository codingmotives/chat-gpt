package com.example.chatgpt.gateway;

import com.example.chatgpt.gateway.model.ChatGPTModel;
import com.example.chatgpt.gateway.model.PromptMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class ChatGPTGateway implements ExternalServiceGateway<String> {

    Logger logger = LoggerFactory.getLogger(ChatGPTGateway.class);

    private static final String CHAT_GPT_MODEL = "gpt-3.5-turbo";
    private static final double CHAT_GPT_TEMPERATURE = 0.5;

    @Autowired
    private ChatGPTModel chatGPTModel;
    @Autowired
    private PromptMessage promptMessage;

    private static final String CHAT_COMPLETION_END_POINT = "https://api.openai.com/v1/chat/completions";
    @Value("${chatgpt.api.key}")
    private String apiKey;

    private void buildChatGPTRequestModel(String prompt) {
        promptMessage.setRole("user");
        promptMessage.setContent("Provide response in json format: " + prompt);
        chatGPTModel.setModel(CHAT_GPT_MODEL);
        chatGPTModel.setTemperature(CHAT_GPT_TEMPERATURE);
        chatGPTModel.setMessages(Arrays.asList(promptMessage));
    }

    @Override
    public ResponseEntity<String> makePostRequest(String requestBody) {
        if (apiKey == null || apiKey.equals("")) {
            throw new RuntimeException("API key is not set");
        }
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        buildChatGPTRequestModel(requestBody);
        HttpEntity<ChatGPTModel> request = new HttpEntity<>(chatGPTModel, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(CHAT_COMPLETION_END_POINT, request, String.class);
            logger.info("Received response from openAI");
            return response;
        } catch (Exception e) {
            logger.error("Error occurred while making rest call to openAI", e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("System error occurred");
        }
    }
}
