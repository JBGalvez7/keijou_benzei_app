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
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class MessagesController {

    @FXML
    private VBox messagesVBox;
    @FXML
    private TextField messageInput;
    @FXML
    private Button sendButton;

    private MessageService messageService;

    public void initialize() {
        messageService = new MessageService();
        loadMessages();
    }

    public void loadMessages() {
        List<Message> messages = messageService.getAllMessages();
        messagesVBox.getChildren().clear();

        for (Message message : messages) {
            Text messageText = new Text(message.getContent());
            messagesVBox.getChildren().add(messageText);
        }
    }

    @FXML
    private void sendMessage() {
        String messageContent = messageInput.getText();

        if (!messageContent.trim().isEmpty()) {
            messageService.sendMessage(messageContent);
            messageInput.clear();
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
        System.out.println("Navigating to Buyer screen...");
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
        System.out.println("You're already at Messages screen...");
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
        System.out.println("Navigating to Buyer screen...");
    }
}
