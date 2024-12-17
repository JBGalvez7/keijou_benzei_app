package com.kb_app.keijou_benzei_app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrdersController {

    @FXML
    private ListView<String> ordersListView;

    public void loadOrders(String buyerUsername) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kb_app_db", "root", "");
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT o.orderID, p.name, o.status, o.dateOrdered, o.totalAmount " +
                             "FROM orders o " +
                             "JOIN product p ON o.productID = p.productID " +
                             "WHERE o.Username = ?"
             )) {
            stmt.setString(1, buyerUsername);
            ResultSet rs = stmt.executeQuery();
            ordersListView.getItems().clear();
            while (rs.next()) {
                int orderID = rs.getInt("orderID");
                String productName = rs.getString("name");
                String status = rs.getString("status");
                String dateOrdered = rs.getString("dateOrdered");
                double totalAmount = rs.getDouble("totalAmount");

                ordersListView.getItems().add("Order ID: " + orderID + "\nProduct: " + productName +
                        "\nStatus: " + status + "\nDate: " + dateOrdered + "\nTotal Amount: " + totalAmount + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void initialize() {
        String buyerUsername = "user";
        loadOrders(buyerUsername);
    }


    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/buyer.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Buyer Selection");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        @FXML
        private void viewMessages (ActionEvent event){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/messages.fxml"));
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
        private void viewCart (ActionEvent event){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cart.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setMaximized(true);
                stage.setTitle("Seller Section");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Navigating to Browse Products screen...");
        }

        @FXML
        private void viewOrders (ActionEvent event){
        }
    }

