package com.kb_app.keijou_benzei_app.models;

import java.sql.Timestamp;

public class Message {

    private int messageID;
    private String content;
    private Timestamp timestamp;

    public Message(int messageID, int senderID, String content, Timestamp timestamp) {
        this.messageID = messageID;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Message(String content) {
        this.content = content;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
