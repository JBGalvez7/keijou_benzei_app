package com.kb_app.keijou_benzei_app.services;

import com.kb_app.keijou_benzei_app.models.Message;
import java.util.ArrayList;
import java.util.List;

public class MessageService {

    // Simulate a backend storage (in reality, you'd interact with a REST API)
    private List<Message> messageDatabase = new ArrayList<>();

    public List<Message> getAllMessages() {
        // This would fetch messages from a backend API
        return messageDatabase;
    }

    public void sendMessage(String content) {
        // This would send a message to the backend API
        Message newMessage = new Message(content);
        messageDatabase.add(newMessage);  // Simulate adding to the database
    }
}

