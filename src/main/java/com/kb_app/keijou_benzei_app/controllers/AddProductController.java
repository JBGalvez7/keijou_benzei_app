package com.kb_app.keijou_benzei_app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddProductController {

    @FXML
    private TextField nameField, priceField, styleField, ratingsField, soldCountField;

    @FXML
    private TextArea pictureField;

    @FXML
    private ComboBox<String> sizeComboBox, agePrefComboBox, genderComboBox, categoryComboBox; // Added categoryComboBox

    @FXML
    private void initialize() {
        loadCategories();
    }

    private void loadCategories() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kb_app_db", "root", "")) {
            String query = "SELECT name FROM category";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String categoryName = rs.getString("name");
                categoryComboBox.getItems().add(categoryName); // Add category to ComboBox
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddProduct() {
        String name = nameField.getText();
        String price = priceField.getText();
        String categoryName = categoryComboBox.getValue(); // Get selected category name
        String style = styleField.getText();
        String size = sizeComboBox.getValue();
        String agePref = agePrefComboBox.getValue();
        String gender = genderComboBox.getValue();
        String picture = pictureField.getText();
        String ratings = ratingsField.getText();
        String soldCount = soldCountField.getText();

        if (name.isEmpty() || price.isEmpty() || categoryName == null || size == null || agePref == null || gender == null) {
            System.out.println("Please fill out all required fields.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kb_app_db", "root", "")) {
            // Fetch categoryID based on the selected category name
            String categoryQuery = "SELECT categoryID FROM category WHERE name = ?";
            PreparedStatement pstmtCategory = conn.prepareStatement(categoryQuery);
            pstmtCategory.setString(1, categoryName);
            ResultSet rsCategory = pstmtCategory.executeQuery();

            int categoryID = 0;
            if (rsCategory.next()) {
                categoryID = rsCategory.getInt("categoryID");
            }

            // Insert the product with the retrieved categoryID
            String query = "INSERT INTO products(name, price, picture_url, categoryID, style, size, agePreference, gender, ratings, soldCount, sellerID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, name);
            pstmt.setBigDecimal(2, new java.math.BigDecimal(price));
            pstmt.setString(3, picture);
            pstmt.setInt(4, categoryID); // Insert categoryID instead of category name
            pstmt.setString(5, style.isEmpty() ? null : style);
            pstmt.setString(6, size);
            pstmt.setString(7, agePref);
            pstmt.setString(8, gender);
            pstmt.setDouble(9, ratings.isEmpty() ? 0.0 : Double.parseDouble(ratings));
            pstmt.setInt(10, soldCount.isEmpty() ? 0 : Integer.parseInt(soldCount));
            pstmt.setInt(11, 1); // Static SellerID for now; replace with actual session data

            pstmt.executeUpdate();
            System.out.println("Product successfully added!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}