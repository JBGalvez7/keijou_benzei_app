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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text errorMessage;

    // Database utility class instance
    private Database database = new Database();

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

    // Method to check if the entered username and password are valid
    private boolean isValidLogin(String username, String password) {
        String query = "SELECT password FROM user WHERE username = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Get the hashed password from the database
                String hashedPassword = rs.getString("password");
                // Check if the entered password matches the hashed password
                return BCrypt.checkpw(password, hashedPassword);
            } else {
                // Username not found
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
