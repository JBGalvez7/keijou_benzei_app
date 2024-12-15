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
        productCategoryLabel.setText("Category: " + category); // Display category name
        productSizeLabel.setText("Size: " + size);
        productGenderLabel.setText("Gender: " + gender);
        productAgePrefLabel.setText("Age Preference: " + agePref);
        productImageView.setImage(new Image(getClass().getResourceAsStream("/" + imagePath)));
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
        // Handle Buy Now Logic
    }

    @FXML
    private void handleAddToCart(ActionEvent event) {
        // Handle Add to Cart Logic
    }
}
