package com.kb_app.keijou_benzei_app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class ProductsController {

    @FXML
    private VBox productsContainer;

    @FXML
    private void initialize() {
        loadProductsFromDatabase();
    }

    private void loadProductsFromDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kb_app_db", "root", "");
             Statement stmt = conn.createStatement()) {

            String query = "SELECT p.productID, p.name, c.name AS Category, p.price " +
                    "FROM products p " +
                    "JOIN category c ON p.categoryID = c.categoryID " +
                    "WHERE p.userID = 1";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                String category = rs.getString("Category");
                double price = rs.getDouble("price");

                Text productInfo = new Text(String.format("%s - %s: ₱%.2f", category, name, price));
                productsContainer.getChildren().add(productInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddProduct() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addProduct.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Product");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Seller.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Seller Section");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Navigating to Seller screen...");
    }

    @FXML
    private void handleProducts(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/products.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Seller - Products");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("You're already at Products screen...");
    }

    @FXML
    private void handleEarningsHistory(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/earningsHistory.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Seller - Earnings History");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Navigating to Earnings History screen");
    }
}
