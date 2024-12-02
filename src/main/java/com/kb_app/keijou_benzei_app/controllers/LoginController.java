package com.kb_app.keijou_benzei_app.controllers;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text errorMessage;


    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isValidLogin(username, password)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/buyerseller.fxml"));
                Scene buyerSellerScene = new Scene(fxmlLoader.load(), 420, 350);

                Stage currentStage = (Stage) usernameField.getScene().getWindow();
                currentStage.setScene(buyerSellerScene);
                currentStage.setTitle("Buyer or Seller");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorMessage.setText("INVALID USERNAME OR PASSWORD. TRY AGAIN");
        }
    }

    private boolean isValidLogin(String username, String password) {
        return username.equals("Chuj2117") && password.equals("211713");
    }
}




