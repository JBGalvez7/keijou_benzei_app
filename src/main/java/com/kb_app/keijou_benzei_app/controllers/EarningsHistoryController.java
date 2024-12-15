package com.kb_app.keijou_benzei_app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class EarningsHistoryController {

    @FXML
    private TableView<EarningRecord> earningsTable;

    @FXML
    private void initialize() {
        loadEarningsHistory();
    }

    private void loadEarningsHistory() {
        ObservableList<EarningRecord> records = FXCollections.observableArrayList(
                new EarningRecord("INV001", "2024-11-20", "Women Korean Black Coat or Blazer", "₱120.00"),
                new EarningRecord("INV002", "2024-11-21", "Original Design Blue V Neck Cross Patchwork Cold Shoulder Cotton Shirt", "₱170.00"),
                new EarningRecord("INV001", "2024-11-21", "Women Korean Black Coat or Blazer", "₱120.00"),
                new EarningRecord("INV002", "2024-11-25", "Women Korean Black Coat or Blazer", "₱120.00"),
                new EarningRecord("INV001", "2024-11-27", "Original Design Blue V Neck Cross Patchwork Cold Shoulder Cotton Shirt", "₱170.00"),
                new EarningRecord("INV002", "2024-12-01", "Original Design Blue V Neck Cross Patchwork Cold Shoulder Cotton Shirt", "₱170.00")

        );

        earningsTable.setItems(records);
    }

    public static class EarningRecord {
        private final String invoiceId;
        private final String date;
        private final String productName;
        private final String amount;

        public EarningRecord(String invoiceId, String date, String productName, String amount) {
            this.invoiceId = invoiceId;
            this.date = date;
            this.productName = productName;
            this.amount = amount;
        }

        public String getInvoiceId() { return invoiceId; }
        public String getDate() { return date; }
        public String getProductName() { return productName; }
        public String getAmount() { return amount; }
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
