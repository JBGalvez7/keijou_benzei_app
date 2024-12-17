package com.kb_app.keijou_benzei_app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddProductsController {

    @FXML
    private TextField nameField, priceField, pictureField;

    private String productIdForEdit = null;


    public void setProductForEdit(String productId, String productName, double price, String picture) {
        productIdForEdit = productId;
        nameField.setText(productName);
        priceField.setText(String.valueOf(price));
        pictureField.setText(picture);
    }

    @FXML
    private void handleSave() {
        String productName = nameField.getText().trim();
        String priceText = priceField.getText().trim();
        String pictureUrl = pictureField.getText().trim();


        if (productName.isEmpty() || priceText.isEmpty()) {
            showAlert("Validation Error", "Product name and price are required.");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Price must be a valid number.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kb_app_db", "root", "")) {
            if (productIdForEdit != null) {
                String updateQuery = "UPDATE product SET name = ?, price = ?, picture = ? WHERE productID = ?";
                try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                    stmt.setString(1, productName);
                    stmt.setDouble(2, price);
                    stmt.setString(3, pictureUrl);
                    stmt.setString(4, productIdForEdit);
                    stmt.executeUpdate();
                }
            } else {
                String insertQuery = "INSERT INTO product (name, price, picture) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                    stmt.setString(1, productName);
                    stmt.setDouble(2, price);
                    stmt.setString(3, pictureUrl);
                    stmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to save the product.");
        }

        ((Stage) nameField.getScene().getWindow()).close();
    }

    @FXML
    private void handleCancel() {
        ((Stage) nameField.getScene().getWindow()).close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
