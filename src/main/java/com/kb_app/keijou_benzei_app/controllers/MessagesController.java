package com.kb_app.keijou_benzei_app.controllers;

import com.kb_app.keijou_benzei_app.models.Message;
import com.kb_app.keijou_benzei_app.services.MessageService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.List;

public class MessagesController {

    @FXML
    private VBox messagesVBox;  // This is where the messages will be displayed
    @FXML
    private TextField messageInput;  // Input field for the message
    @FXML
    private Button sendButton;  // Send button

    private MessageService messageService;  // Service to handle messages (could be an API call)

    // Initialize the controller, connect to the MessageService
    public void initialize() {
        messageService = new MessageService();
        loadMessages();
    }

    // Method to load messages (e.g., from an API)
    public void loadMessages() {
        List<Message> messages = messageService.getAllMessages();  // Fetch all messages from service

        // Clear the existing messages
        messagesVBox.getChildren().clear();

        // Display messages dynamically in the VBox
        for (Message message : messages) {
            Text messageText = new Text(message.getContent());
            messagesVBox.getChildren().add(messageText);
        }
    }

    // Method to handle sending a message
    @FXML
    private void sendMessage() {
        String messageContent = messageInput.getText();

        if (!messageContent.trim().isEmpty()) {
            // Send the message using the service
            messageService.sendMessage(messageContent);

            // Clear the input field
            messageInput.clear();

            // Reload messages after sending
            loadMessages();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/buyer.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Buyer Section");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Navigating to Browse Products screen...");
    }

    @FXML
    private void handleMessages(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/messages.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Buyer - Messages");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Navigating to Messages screen...");
    }

    @FXML
    private void handleOrders(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/orders.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Buyer - Orders");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Navigating to Orders screen...");
    }
    @FXML
    private void handleBrowse(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/buyer.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Buyer Section");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Navigating to Browse Products screen...");
    }
}
