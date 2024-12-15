package com.kb_app.keijou_benzei_app.models;

import java.sql.Timestamp;

public class Message {

    private int messageID;
    private int senderID;
    private String content;
    private Timestamp timestamp;

    public Message(int messageID, int senderID, String content, Timestamp timestamp) {
        this.messageID = messageID;
        this.senderID = senderID;
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

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
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
