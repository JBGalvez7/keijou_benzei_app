package com.kb_app.keijou_benzei_app.controllers;

import com.kb_app.keijou_benzei_app.models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductsController {
    @FXML
    private TableView<Product> productsTable;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private void initialize() {
        // Set up columns
        TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, String> pictureColumn = new TableColumn<>("Picture");
        pictureColumn.setCellValueFactory(new PropertyValueFactory<>("picture"));

        // Add columns to the table
        productsTable.getColumns().addAll(nameColumn, priceColumn, pictureColumn);

        // Add a listener to enable/disable buttons based on selected row
        productsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
            } else {
                editButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });

        // Load products into the table
        loadProductsFromDatabase();
    }

    // Load products from the database
    private void loadProductsFromDatabase() {
        productsTable.getItems().clear();
        String query = "SELECT * FROM product";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kb_app_db", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("productID"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("picture")
                );
                productsTable.getItems().add(product);
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
            stage.setTitle("Add Product");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditProduct() {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            System.out.println("Editing product: " + selectedProduct.getProductName());

            // Open the AddProduct form to edit this product
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addProduct.fxml"));
                Parent root = loader.load();
                AddProductsController addProductsController = loader.getController();

                // Pass product data for editing
                addProductsController.setProductForEdit(
                        String.valueOf(selectedProduct.getProductID()),
                        selectedProduct.getProductName(),
                        selectedProduct.getPrice(),
                        selectedProduct.getPicture()
                );

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Edit Product");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            // Confirm deletion with a dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Product");
            alert.setHeaderText("Are you sure you want to delete this product?");
            alert.setContentText("Product: " + selectedProduct.getProductName());

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Delete product from database
                    String deleteQuery = "DELETE FROM product WHERE productID = ?";
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kb_app_db", "root", "");
                         PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

                        stmt.setInt(1, selectedProduct.getProductID());
                        stmt.executeUpdate();

                        // Remove product from table view
                        productsTable.getItems().remove(selectedProduct);
                        System.out.println("Product deleted: " + selectedProduct.getProductName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
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
        System.out.println("Navigating to Products screen...");
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
        System.out.println("You're already at Earnings History screen...");
    }
}