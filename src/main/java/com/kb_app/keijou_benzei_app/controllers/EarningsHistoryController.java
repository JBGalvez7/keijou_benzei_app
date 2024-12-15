package com.kb_app.keijou_benzei_app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EarningsHistoryController {

    @FXML
    private TableView<EarningRecord> earningsTable;

    @FXML
    private void initialize() {
        loadEarningsHistory();
    }

    private void loadEarningsHistory() {
        ObservableList<EarningRecord> records = FXCollections.observableArrayList(
                new EarningRecord("INV001", "2024-06-20", "Phone", "₱5000"),
                new EarningRecord("INV002", "2024-06-21", "Laptop", "₱35000")
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
}
