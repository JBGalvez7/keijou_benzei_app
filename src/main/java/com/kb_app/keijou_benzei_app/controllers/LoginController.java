package com.kb_app.keijou_benzei_app.controllers;

import com.kb_app.keijou_benzei_app.utility.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void handleLogin(ActionEvent event) {
        String username = username.getText();
        String password = password.getText();

        if(authenticateUser(username,password)){

        }

    }

    private boolean authenticateUser(String username, String password) {
        Database db = new Database();
        String query = "SELECT username, password FROM users";
        try {
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String dbPassword = rs.getString("password");
            }
            ps.executeUpdate();
            System.out.println("New user created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
