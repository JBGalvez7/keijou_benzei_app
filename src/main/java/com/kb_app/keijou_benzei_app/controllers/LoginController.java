package com.kb_app.keijou_benzei_app.controllers;

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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/purchaseManage.fxml"));
                Scene buyerSellerScene = new Scene(fxmlLoader.load());

                Stage currentStage = (Stage) usernameField.getScene().getWindow();
                currentStage.setScene(buyerSellerScene);
                currentStage.setTitle("Buyer/Seller Selection");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorMessage.setVisible(true);
        }
    }


    private boolean isValidLogin(String username, String password) {
        return username.equals("user") && password.equals("1234");
    }
}