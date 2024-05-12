package com.example.chatgpt.controller;

import com.example.chatgpt.exceptions.InvalidInputException;
import com.example.chatgpt.model.ChatRequestModel;
import com.example.chatgpt.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatServiceImpl;

    Logger logger = LoggerFactory.getLogger(ChatController.class);

    @PostMapping("chat/completions")
    public ResponseEntity<String> getResponse(@RequestBody ChatRequestModel chatRequestModel) {
        try {
            String response = chatServiceImpl.getChatResponse(chatRequestModel.getUserQuery());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (InvalidInputException e) {
            logger.error("Invalid query message - {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception ex) {
            logger.error("error occurred in processing request", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("System error occurred");
        }
    }
}
