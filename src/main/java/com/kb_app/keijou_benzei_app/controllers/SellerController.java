package com.kb_app.keijou_benzei_app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public class SellerController {

    @FXML
    private Label lblTodayIncome, lblTotalIncome, lblProductsSold;

    @FXML
    private LineChart<String, Number> incomeChart;

    @FXML
    private BarChart<String, Number> productSalesChart;

    @FXML
    private TableView<TopProduct> topProductsTable;

    @FXML
    private TableView<RecentOrder> recentOrdersTable;

    private ObservableList<RecentOrder> recentOrders;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/kb_app_db"; // Your DB URL
    private static final String DB_USERNAME = "root"; // Your DB username
    private static final String DB_PASSWORD = ""; // Your DB password

    @FXML
    private void initialize() {
        loadSellerStats();
        loadIncomeChart();
        loadProductSalesChart();
        loadRecentOrders();
        loadTopProducts();
    }

    private void loadSellerStats() {

        double todayIncome = calculateTodayIncome();
        double totalIncome = todayIncome + 12527.45;
        int productsSold = calculateTotalProductsSold() + 247;

        lblTodayIncome.setText("₱" + String.format("%.2f", todayIncome));
        lblTotalIncome.setText("₱" + String.format("%.2f", totalIncome));
        lblProductsSold.setText(String.valueOf(productsSold));
    }

    private double calculateTodayIncome() {
        double todayIncome = 0;
        String query = "SELECT totalAmount FROM orders WHERE DateOrdered = CURDATE()";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                todayIncome += rs.getDouble("totalAmount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todayIncome;
    }

    private int calculateTotalProductsSold() {
        int totalProductsSold = 0;
        String query = "SELECT Quantity FROM orders";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                totalProductsSold += rs.getInt("Quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalProductsSold;
    }

    private void loadIncomeChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Income Over Time");

        series.getData().add(new XYChart.Data<>("2024-10-20", 70));
        series.getData().add(new XYChart.Data<>("2024-11-22", 160));
        series.getData().add(new XYChart.Data<>("2024-12-02", 60));

        incomeChart.getData().add(series);
    }

    private void loadProductSalesChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Products Sold");

        series.getData().add(new XYChart.Data<>("2024-10-20", 5));
        series.getData().add(new XYChart.Data<>("2024-11-22", 14));
        series.getData().add(new XYChart.Data<>("2024-12-02", 3));

        productSalesChart.getData().add(series);
    }

    private void loadRecentOrders() {
        recentOrders = FXCollections.observableArrayList();
        String query = "SELECT * FROM orders ORDER BY DateOrdered DESC LIMIT 5";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String orderId = rs.getString("OrderID");
                String customerName = rs.getString("Username");
                double amount = rs.getDouble("totalAmount");
                String productName = "Product " + rs.getString("ProductID");
                int quantity = rs.getInt("Quantity");

                recentOrders.add(new RecentOrder(orderId, customerName, amount, productName, quantity));
            }

            TableColumn<RecentOrder, String> orderIdCol = new TableColumn<>("Order ID");
            orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));

            TableColumn<RecentOrder, String> customerNameCol = new TableColumn<>("Customer");
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));

            TableColumn<RecentOrder, Double> amountCol = new TableColumn<>("Amount");
            amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

            TableColumn<RecentOrder, String> productCol = new TableColumn<>("Product");
            productCol.setCellValueFactory(new PropertyValueFactory<>("productName"));

            TableColumn<RecentOrder, Integer> quantityCol = new TableColumn<>("Quantity");
            quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            recentOrdersTable.getColumns().addAll(orderIdCol, customerNameCol, amountCol, productCol, quantityCol);
            recentOrdersTable.setItems(recentOrders);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTopProducts() {

        Map<String, Integer> productSalesCount = new HashMap<>();
        String query = "SELECT ProductID, SUM(Quantity) AS totalSales FROM orders GROUP BY ProductID ORDER BY totalSales DESC LIMIT 5";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String productId = rs.getString("ProductID");
                int totalSales = rs.getInt("totalSales");
                String productName = "Product " + productId;
                productSalesCount.put(productName, totalSales);
            }

            ObservableList<TopProduct> sortedTopProducts = FXCollections.observableArrayList(
                    productSalesCount.entrySet().stream()
                            .map(entry -> new TopProduct(entry.getKey(), entry.getValue()))
                            .collect(Collectors.toList())
            );

            TableColumn<TopProduct, String> productNameCol = new TableColumn<>("Product Name");
            productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));

            TableColumn<TopProduct, Integer> salesCol = new TableColumn<>("Sales");
            salesCol.setCellValueFactory(new PropertyValueFactory<>("sales"));

            topProductsTable.getColumns().addAll(productNameCol, salesCol);
            topProductsTable.setItems(sortedTopProducts);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        navigateTo(event, "/fxml/purchaseManage.fxml", "Buyer/Seller Selection");
    }

    @FXML
    private void handleProducts(ActionEvent event) {
        navigateTo(event, "/fxml/products.fxml", "Seller - Products");
    }

    @FXML
    private void handleEarningsHistory(ActionEvent event) {
        navigateTo(event, "/fxml/earningsHistory.fxml", "Seller - Earnings History");
    }

    private void navigateTo(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static class TopProduct {
        private final String productName;
        private final int sales;

        public TopProduct(String productName, int sales) {
            this.productName = productName;
            this.sales = sales;
        }

        public String getProductName() {
            return productName;
        }

        public int getSales() {
            return sales;
        }
    }


    public static class RecentOrder {
        private final String orderId;
        private final String customerName;
        private final double amount;
        private final String productName;
        private final int quantity;

        public RecentOrder(String orderId, String customerName, double amount, String productName, int quantity) {
            this.orderId = orderId;
            this.customerName = customerName;
            this.amount = amount;
            this.productName = productName;
            this.quantity = quantity;
        }

        public String getOrderId() {
            return orderId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public double getAmount() {
            return amount;
        }

        public String getProductName() {
            return productName;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
