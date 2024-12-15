package com.kb_app.keijou_benzei_app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class SellerController {

    @FXML
    private Label lblTodayIncome, lblTotalIncome, lblProductsSold;

    @FXML
    private LineChart<String, Number> incomeChart;

    @FXML
    private BarChart<String, Number> productSalesChart;

    @FXML
    public void initialize() {
        loadSellerStats();
        loadIncomeChart();
        loadProductSalesChart();
    }

    private void loadSellerStats() {
        lblTodayIncome.setText("₱1989.13");
        lblTotalIncome.setText("₱21,117.85");
        lblProductsSold.setText("66");
    }

    private void loadIncomeChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Income Over Time");

        series.getData().add(new XYChart.Data<>("2024-010-20", 70));
        series.getData().add(new XYChart.Data<>("2024-011-22", 160));
        series.getData().add(new XYChart.Data<>("2024-012-02", 60));

        incomeChart.getData().add(series);
    }

    private void loadProductSalesChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Products Sold");

        series.getData().add(new XYChart.Data<>("2024-010-20", 5));
        series.getData().add(new XYChart.Data<>("2024-011-22", 14));
        series.getData().add(new XYChart.Data<>("2024-012-02", 3));

        productSalesChart.getData().add(series);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        navigateTo(event, "/fxml/buyerseller.fxml", "Buyer/Seller Selection");
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
}
