package com.kb_app.keijou_benzei_app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin(ActionEvent event) {
        if (usernameField == null || passwordField == null) {
            System.out.println("Fields are not initialized!");
            return;
        }

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticateUser(username, password)) {
            System.out.println("Login successful");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password.");
            alert.showAndWait();
        }
    }

    private boolean authenticateUser(String username, String password) {
        // Authentication logic
        return true; // Replace with actual logic
    }
}


