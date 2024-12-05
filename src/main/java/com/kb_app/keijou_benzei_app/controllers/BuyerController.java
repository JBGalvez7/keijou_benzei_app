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
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/buyerseller.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Buyer/Seller Selection");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewOrders(ActionEvent event) {
    }

    public void viewMessages(ActionEvent event) {
    }

    public void browseProducts(ActionEvent event) {
    }

    @FXML
    public void initialize() {
        product1ImageView.setImage(loadImage("images/FNciNkcXsAExu5w.png"));
        product2ImageView.setImage(loadImage("images/20231110_170958.jpg"));
        product3ImageView.setImage(loadImage("images/GdliQsrWYAAO7ge.jpg"));
        product4ImageView.setImage(loadImage("images/GdliQsrWYAAO7ge.jpg"));
    }

    private Image loadImage(String relativePath) {
        InputStream imageStream = getClass().getResourceAsStream("/" + relativePath);
        if (imageStream != null) {
            return new Image(imageStream);
        } else {
            System.err.println("Image not found: " + relativePath);
            return null;
        }
    }
}