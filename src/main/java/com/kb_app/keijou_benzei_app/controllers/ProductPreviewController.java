package com.kb_app.keijou_benzei_app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class ProductPreviewController {

    @FXML private ImageView productImageView;
    @FXML private Text productNameText;
    @FXML private Text productPriceText;
    @FXML private ComboBox<String> sizeComboBox;
    @FXML private TextField quantityTextField;

    private int productID;
    private double productPrice;
    private String productName;

    // Initialize method receives the productID from BuyerController
    public void initialize(int productID) {
        this.productID = productID;
        loadProductDetails();
    }

    // Load product details based on productID
    private void loadProductDetails() {
        String[] productNames = {
                "CICOSAT Criminology Gala Uniform (Pants)", "CICOSAT Criminology Gala Uniform (Top)",
                "DMMMSU Computer Science Departmental Uniform (Top)", "DMMMSU Computer Science Departmental Uniform (Pants)",
                "Lorma Colleges Institutional Uniform", "Lorma Colleges PE Uniform",
                "Lorma Colleges Nursing Departmental Uniform", "Lorma Colleges CCSE Departmental Uniform",
                "Lorma Colleges Nursing Gala Uniform (Female)", "SLC PE Shirt",
                "SLC Gala Uniform (Female - Skirt)", "SLC Gala Uniform (Female - Top)",
                "SLC CSA Departmental Uniform", "SLC CEA Departmental Uniform",
                "DMMMSU Pathfit Uniform", "SLC NSTP Uniform"
        };

        double[] productPrices = { 250, 450, 300, 200, 250, 200, 300, 300, 500, 250, 250, 300, 300, 300, 250, 250 };

        String[] imagePaths = {
                "CICOSAT Criminology Gala Uniform (Pants).jpg",
                "CICOSAT Criminology Gala Uniform (Top).jpg",
                "DMMMSU Computer Science Departmental Uniform (Top).jpg",
                "DMMMSU Computer Science Departmental Uniform (Pants).jpg",
                "Lorma Colleges Institutional Uniform.jpg",
                "Lorma Colleges PE Uniform.jpg",
                "Lorma Colleges Nursing Departmental Uniform.jpg",
                "Lorma Colleges CCSE Departmental Uniform.jpg",
                "Lorma Colleges Nursing Gala Uniform (Female).jpg",
                "SLC PE Shirt.jpg",
                "SLC Gala Uniform (Female - Skirt).jpg",
                "SLC Gala Uniform (Female - Top).jpg",
                "SLC CSA Departmental Uniform.jpg",
                "SLC CEA Departmental Uniform.jpg",
                "DMMMSU Pathfit Uniform.jpg",
                "SLC NSTP Uniform.jpg"
        };


        productName = productNames[productID];
        productPrice = productPrices[productID];

        productNameText.setText(productName);
        productPriceText.setText("₱" + productPrice);
        productImageView.setImage(new Image(getClass().getResourceAsStream("/images/" + imagePaths[productID])));


        sizeComboBox.getItems().clear();
        sizeComboBox.getItems().addAll("XS", "S", "M", "L", "XL");
    }



    @FXML
    private void addToCart() {
        String selectedSize = sizeComboBox.getValue();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityTextField.getText());
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity.");
            return;
        }

        if (selectedSize == null || selectedSize.isEmpty()) {
            System.out.println("Please select a size.");
            return;
        }

        double totalAmount = productPrice * quantity;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kb_app_db", "root", "")) {
            String checkProductQuery = "SELECT COUNT(*) FROM product WHERE productID = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkProductQuery)) {
                checkStmt.setInt(1, productID);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {

                    String query = "INSERT INTO cart (Username, productID, Quantity, Size, totalAmount, Status) " +
                            "VALUES (?, ?, ?, ?, ?, 'In Cart');";
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, "user");
                        stmt.setInt(2, productID);
                        stmt.setInt(3, quantity);
                        stmt.setString(4, selectedSize);
                        stmt.setDouble(5, totalAmount);
                        stmt.executeUpdate();
                        System.out.println("Added to cart successfully.");
                    }
                } else {

                    System.out.println("Product not found.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void buyNow() {
        String selectedSize = sizeComboBox.getValue();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityTextField.getText());
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity.");
            return;
        }

        if (selectedSize == null || selectedSize.isEmpty()) {
            System.out.println("Please select a size.");
            return;
        }

        double totalAmount = productPrice * quantity;
        System.out.println("Total amount: ₱" + totalAmount);

        TextInputDialog cashDialog = new TextInputDialog();
        cashDialog.setTitle("Enter Cash Amount");
        cashDialog.setHeaderText("Total amount: ₱" + totalAmount);
        cashDialog.setContentText("Please enter your cash amount:");

        Optional<String> result = cashDialog.showAndWait();

        if (result.isPresent()) {
            try {
                double cash = Double.parseDouble(result.get());
                if (cash < totalAmount) {
                    System.out.println("Insufficient amount. Please enter enough money.");
                    return;
                }

                double change = cash - totalAmount;
                System.out.println("Change: ₱" + change);

                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kb_app_db", "root", "")) {
                    String query = "INSERT INTO orders (Username, productID, Status, Quantity, DateOrdered, Size, totalAmount) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?);";
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, "user");
                        stmt.setInt(2, productID);
                        stmt.setString(3, "To Ship");
                        stmt.setInt(4, quantity);
                        stmt.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
                        stmt.setString(6, selectedSize);
                        stmt.setDouble(7, totalAmount);

                        stmt.executeUpdate();
                        System.out.println("Order placed successfully.");
                        System.out.println("Change: ₱" + change);

                        Stage stage = (Stage) productNameText.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/orders.fxml"));
                        Parent root = loader.load();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Your Orders");
                        stage.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid cash amount. Please enter a valid number.");
            }
        }
    }


    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/buyer.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) productNameText.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Buyer Section");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
