package com.kb_app.keijou_benzei_app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

public class ProductPreviewController {

    @FXML
    private ImageView productImageView;
    @FXML
    private Label productNameLabel, productPriceLabel, productCategoryLabel, productSizeLabel, productGenderLabel, productAgePrefLabel;

    @FXML
    private Button buyNowButton, addToCartButton;

    private int productId;

    public void setProductDetails(int id, String name, String price, String category, String size, String gender, String agePref, String imagePath) {
        productId = id;
        productNameLabel.setText(name);
        productPriceLabel.setText("Price: " + price);
        productCategoryLabel.setText("Category: " + category);
        productSizeLabel.setText("Size: " + size);
        productGenderLabel.setText("Gender: " + gender);
        productAgePrefLabel.setText("Age Preference: " + agePref);

        InputStream imageStream = getClass().getResourceAsStream("/" + imagePath);
        if (imageStream == null) {
            System.out.println("Image not found: " + imagePath);
        } else {
            productImageView.setImage(new Image(imageStream));
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Stage stage = (Stage) buyNowButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/buyer.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Buyer Section");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleOrders(ActionEvent event) {
        try {
            Stage stage = (Stage) buyNowButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/orders.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Buyer - Orders");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMessages(ActionEvent event) {
        try {
            Stage stage = (Stage) buyNowButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/messages.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Buyer - Messages");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBuyNow(ActionEvent event) {
        int buyerId = 1;
        int quantity = 1;
        String status = "To Pay";

        if (productId <= 0) {
            System.out.println("Invalid product ID: " + productId);
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kb_app_db", "root", "");
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO orders (productID, buyerID, status, quantity, dateOrdered) VALUES (?, ?, ?, ?, NOW())"
             )) {
            stmt.setInt(1, productId);
            stmt.setInt(2, buyerId);
            stmt.setString(3, status);
            stmt.setInt(4, quantity);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Order placed successfully!");
            } else {
                System.out.println("Order placement failed.");
            }

            System.out.println("Inserted Product ID: " + productId + ", Buyer ID: " + buyerId);

            Stage stage = (Stage) buyNowButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/orders.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Buyer - Orders");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Foreign key constraint violation. Ensure productID exists in the products table.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddToCart(ActionEvent event) {
    }
}