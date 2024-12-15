package com.kb_app.keijou_benzei_app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kb_app_db", "root", "");
            Statement stmt = conn.createStatement();
            // Fetch product details along with category name by joining products and category tables
            String query = "SELECT p.Name, c.name AS Category, p.Price FROM products p " +
                    "JOIN category c ON p.categoryID = c.categoryID";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("Name");
                String category = rs.getString("Category"); // Get the category name
                double price = rs.getDouble("Price");

                Text productInfo = new Text(String.format("%s - %s: ₱%.2f", category, name, price));
                productsContainer.getChildren().add(productInfo);
            }

            conn.close();
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
}
