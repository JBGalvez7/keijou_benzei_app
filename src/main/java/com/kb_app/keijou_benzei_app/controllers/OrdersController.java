package com.kb_app.keijou_benzei_app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class OrdersController {

    @FXML
    private VBox ordersContainer;

    public void initialize() {
        loadOrders();
    }

    private void loadOrders() {
        ordersContainer.getChildren().clear();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kb_app_db", "root", "");
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT o.orderID, o.status, o.quantity, o.dateOrdered, p.name, p.price, p.picture_url " +
                             "FROM orders o " +
                             "JOIN products p ON o.productID = p.productID " +
                             "WHERE o.buyerID = ?"
             )) {
            int buyerId = 1;
            stmt.setInt(1, buyerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("orderID");
                String productName = rs.getString("name");
                String status = rs.getString("status");
                String imageUrl = rs.getString("picture_url");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                String dateOrdered = rs.getString("dateOrdered");

                HBox orderItem = createOrderItem(orderId, productName, status, imageUrl, quantity, price, dateOrdered);
                ordersContainer.getChildren().add(orderItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HBox createOrderItem(int orderId, String productName, String status, String imageUrl, int quantity, double price, String dateOrdered) {
        HBox hbox = new HBox(10);
        hbox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10;");

        ImageView productImage = new ImageView(new Image(getClass().getResourceAsStream(imageUrl)));
        productImage.setFitHeight(100);
        productImage.setFitWidth(100);

        VBox orderDetails = new VBox(5);
        Text orderInfo = new Text("Order #" + orderId + " - " + productName);
        orderInfo.setStyle("-fx-font-weight: bold;");
        Text orderStatus = new Text("Status: " + status);
        Text orderPrice = new Text("Total: $" + (price * quantity));
        Text orderDate = new Text("Ordered on: " + dateOrdered);

        orderDetails.getChildren().addAll(orderInfo, orderStatus, orderPrice, orderDate);
        hbox.getChildren().addAll(productImage, orderDetails);

        return hbox;
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
    private void viewMessages(ActionEvent event) {
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
    private void browseProducts(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/buyer.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Seller Section");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Navigating to Browse Products screen...");
    }

    @FXML
    private void viewOrders(ActionEvent event) {
    }


}