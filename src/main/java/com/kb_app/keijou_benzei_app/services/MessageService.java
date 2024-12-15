package com.kb_app.keijou_benzei_app.services;

import com.kb_app.keijou_benzei_app.models.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageService {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/kb_app_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT content FROM message ORDER BY timestamp ASC";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String content = rs.getString("content");
                messages.add(new Message(content));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

    public void sendMessage(String content) {
        String query = "INSERT INTO message (senderID, content) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, 1);
            pstmt.setString(2, content);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
