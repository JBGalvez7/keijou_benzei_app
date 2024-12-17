package com.kb_app.keijou_benzei_app.controllers;

import com.kb_app.keijou_benzei_app.utility.DBUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class CartController {

    @FXML
    private ListView<String> cartListView;

    @FXML
    private Label totalLabel;

    private double totalAmount = 0.0;

    public void loadCart(String buyerUsername) {
        totalAmount = 0.0;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT c.cartID, p.name, c.quantity, c.size, c.totalAmount " +
                             "FROM cart c " +
                             "JOIN product p ON c.productID = p.productID " +
                             "WHERE c.Username = ?"
             )) {
            stmt.setString(1, buyerUsername);
            ResultSet rs = stmt.executeQuery();
            cartListView.getItems().clear();
            while (rs.next()) {
                int cartID = rs.getInt("cartID");
                String productName = rs.getString("name");
                int quantity = rs.getInt("quantity");
                String size = rs.getString("size");
                double itemTotal = rs.getDouble("totalAmount");

                cartListView.getItems().add("Cart ID: " + cartID + "\nProduct: " + productName +
                        "\nQuantity: " + quantity + "\nSize: " + size + "\nTotal: ₱" + itemTotal + "\n");

                totalAmount += itemTotal;
            }
            totalLabel.setText("Total Amount: ₱" + totalAmount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        String buyerUsername = "user";
        loadCart(buyerUsername);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/buyer.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Buyer Section");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        String selectedItem = cartListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            System.out.println("No item selected.");
            return;
        }

        int cartID = Integer.parseInt(selectedItem.split("Cart ID: ")[1].split("\n")[0]);

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM cart WHERE cartID = ?")) {
            stmt.setInt(1, cartID);
            stmt.executeUpdate();
            System.out.println("Item removed from cart.");
            loadCart("user");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProceedToBuy(ActionEvent event) {
        String buyerUsername = "user";
        if (totalAmount == 0) {
            showAlert("Cart is empty", "Please add items to your cart before proceeding.");
            return;
        }

        TextInputDialog cashDialog = new TextInputDialog();
        cashDialog.setTitle("Enter Cash Amount");
        cashDialog.setHeaderText("Total amount: ₱" + totalAmount);
        cashDialog.setContentText("Please enter your cash amount:");

        Optional<String> result = cashDialog.showAndWait();

        if (result.isPresent()) {
            try {
                double cash = Double.parseDouble(result.get());

                if (cash < totalAmount) {
                    showAlert("Insufficient Funds", "The amount entered is not enough to complete the purchase.");
                    return;
                }

                double change = cash - totalAmount;
                System.out.println("Change: ₱" + change);

                processOrder(buyerUsername, change);

            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid number for the cash amount.");
            }
        }
    }

    private void processOrder(String buyerUsername, double change) {
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            String orderQuery = "INSERT INTO orders (Username, productID, Status, Quantity, DateOrdered, Size, totalAmount) " +
                    "SELECT c.Username, c.productID, 'To Ship', c.quantity, ?, c.size, c.totalAmount " +
                    "FROM cart c " +
                    "JOIN user u ON c.Username = u.Username " +
                    "WHERE c.Username = ?";

            try (PreparedStatement orderStmt = conn.prepareStatement(orderQuery)) {
                orderStmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                orderStmt.setString(2, buyerUsername);
                int rowsInserted = orderStmt.executeUpdate();

                if (rowsInserted == 0) {
                    System.out.println("No valid items found for this user.");
                    conn.rollback();
                    return;
                }

                String clearCartQuery = "DELETE FROM cart WHERE Username = ?";
                try (PreparedStatement clearStmt = conn.prepareStatement(clearCartQuery)) {
                    clearStmt.setString(1, buyerUsername);
                    clearStmt.executeUpdate();
                }

                conn.commit();
                System.out.println("Checkout successful. Change: ₱" + change);

                loadCart(buyerUsername); // Refresh cart

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                showAlert("Checkout Failed", "There was an issue processing your order. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error occurred while connecting to the database. Please try again later.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

