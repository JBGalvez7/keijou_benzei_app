package com.kb_app.keijou_benzei_app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.InputStream;

public class BuyerController {

    @FXML
    private ImageView product1ImageView;
    @FXML
    private ImageView product2ImageView;
    @FXML
    private ImageView product3ImageView;
    @FXML
    private ImageView product4ImageView;
    @FXML
    private ImageView product5ImageView;
    @FXML
    private ImageView product6ImageView;
    @FXML
    private ImageView product7ImageView;
    @FXML
    private ImageView product8ImageView;
    @FXML
    private ImageView product9ImageView;
    @FXML
    private ImageView product10ImageView;

    @FXML
    private void handleBack(ActionEvent event) {
        navigateToScene(event, "/fxml/buyerseller.fxml", "Buyer/Seller Selection");
    }

    public void viewOrders(ActionEvent event) {
        navigateToScene(event, "/fxml/orders.fxml", "Orders");
    }

    public void viewMessages(ActionEvent event) {
        navigateToScene(event, "/fxml/messages.fxml", "Messages");
    }

    public void browseProducts(ActionEvent event) {
        navigateToScene(event, "/fxml/buyer.fxml", "Buyer Section");
    }

    private void navigateToScene(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Initialize product images and set click event listeners
    @FXML
    public void initialize() {
        product1ImageView.setImage(loadImage("images/Korean Style Silk Short Sleeves button mens shirt.jpg"));
        product2ImageView.setImage(loadImage("images/WOMEN KOREAN BLACK COAT or BLAZER.jpg"));
        product3ImageView.setImage(loadImage("images/Women's Graphic Oversized Crewneck Long Sleeve Casual Loose Pullover Los Angeles California Vintage Sweatshirts Coffee Brown Plus Size.jpg"));
        product4ImageView.setImage(loadImage("images/Navy Blue Shirt Women New Autumn High End Temperament V Neck.jpg"));
        product5ImageView.setImage(loadImage("images/5.12Original Design Blue V Neck Cross Patchwork Cold Shoulder Cotton Shirts Spring.jpg"));
        product6ImageView.setImage(loadImage("images/VERTICAL KNITTED SHORT SLEEVE COLLAR SHIRT.jpg"));
        product7ImageView.setImage(loadImage("images/Oversized Multi Pocket Half Shirt.jpg"));
        product8ImageView.setImage(loadImage("images/Young Girl Plaid Print Belted Dress.jpg"));
        product9ImageView.setImage(loadImage("images/Men's Striped Polo Shirt Short Sleeve Summer Lapel Ice Silk Korean-style T-Shirt.jpg"));
        product10ImageView.setImage(loadImage("images/1pc Young Boy Casual College Style Crown Pattern Long Sleeve Polo Shirt.jpg"));

        product1ImageView.setOnMouseClicked(event -> handleProductClick(1, "images/Korean Style Silk Short Sleeves button mens shirt.jpg"));
        product2ImageView.setOnMouseClicked(event -> handleProductClick(2, "images/WOMEN KOREAN BLACK COAT or BLAZER.jpg"));
        product3ImageView.setOnMouseClicked(event -> handleProductClick(3, "images/Women's Graphic Oversized Crewneck Long Sleeve Casual Loose Pullover Los Angeles California Vintage Sweatshirts Coffee Brown Plus Size.jpg"));
        product4ImageView.setOnMouseClicked(event -> handleProductClick(4, "images/Navy Blue Shirt Women New Autumn High End Temperament V Neck.jpg"));
        product5ImageView.setOnMouseClicked(event -> handleProductClick(5, "images/5.12Original Design Blue V Neck Cross Patchwork Cold Shoulder Cotton Shirts Spring.jpg"));
        product6ImageView.setOnMouseClicked(event -> handleProductClick(6, "images/VERTICAL KNITTED SHORT SLEEVE COLLAR SHIRT.jpg"));
        product7ImageView.setOnMouseClicked(event -> handleProductClick(7, "images/Oversized Multi Pocket Half Shirt.jpg"));
        product8ImageView.setOnMouseClicked(event -> handleProductClick(8, "images/Young Girl Plaid Print Belted Dress.jpg"));
        product9ImageView.setOnMouseClicked(event -> handleProductClick(9, "images/Men's Striped Polo Shirt Short Sleeve Summer Lapel Ice Silk Korean-style T-Shirt.jpg"));
        product10ImageView.setOnMouseClicked(event -> handleProductClick(10, "images/1pc Young Boy Casual College Style Crown Pattern Long Sleeve Polo Shirt.jpg"));
    }

    private Image loadImage(String relativePath) {
        InputStream imageStream = getClass().getResourceAsStream("/" + relativePath);
        if (imageStream != null) {
            return new Image(imageStream);
        } else {
            System.err.println("Image not found: " + relativePath);
            return new Image(getClass().getResource("/images/default_placeholder.jpg").toExternalForm());
        }
    }

    private void handleProductClick(int productId, String imagePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/productPreview.fxml"));
            Parent root = loader.load();

            ProductPreviewController controller = loader.getController();
            controller.setProductDetails(
                    productId,
                    "Product " + productId,
                    "100.00",
                    "Sample Category",
                    "M",
                    "Unisex",
                    "Adult",
                    "This is a sample description for product " + productId + ".",
                    imagePath
            );

            Stage stage = (Stage) product1ImageView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Product Preview");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
