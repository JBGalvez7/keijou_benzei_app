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
import com.kb_app.keijou_benzei_app.utility.Database;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text errorMessage;

    private Database database = new Database();  // Initialize the database connection

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
                currentStage.setTitle("Buyer/Seller Selection");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorMessage.setText("Invalid Username or Password. Try Again");
        }
    }

    private boolean isValidLogin(String username, String password) {
        try (Connection conn = database.getConnection()) {
            String query = "SELECT password FROM user WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    return BCrypt.checkpw(password, storedPassword);  // Compare the hashed password
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;  // Return false if no matching user was found
    }
}