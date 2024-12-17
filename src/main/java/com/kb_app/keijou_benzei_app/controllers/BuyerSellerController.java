package com.kb_app.keijou_benzei_app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BuyerSellerController {
    private Stage stage;
    private Scene scene;

    @FXML
    private void handleBuyer (ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/buyer.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Seller - Messages");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Navigating to Messages screen...");
    }

    @FXML
    private void handleSeller (ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/seller.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Seller - Messages");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Navigating to Messages screen...");
    }
}




