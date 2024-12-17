package com.kb_app.keijou_benzei_app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class BuyerController {

    @FXML private ImageView product1ImageView;
    @FXML private ImageView product2ImageView;
    @FXML private ImageView product3ImageView;
    @FXML private ImageView product4ImageView;
    @FXML private ImageView product5ImageView;
    @FXML private ImageView product6ImageView;
    @FXML private ImageView product7ImageView;
    @FXML private ImageView product8ImageView;
    @FXML private ImageView product9ImageView;
    @FXML private ImageView product10ImageView;
    @FXML private ImageView product11ImageView;
    @FXML private ImageView product12ImageView;
    @FXML private ImageView product13ImageView;
    @FXML private ImageView product14ImageView;
    @FXML private ImageView product15ImageView;
    @FXML private ImageView product16ImageView;

    @FXML private Text product1Text;
    @FXML private Text product2Text;
    @FXML private Text product3Text;
    @FXML private Text product4Text;
    @FXML private Text product5Text;
    @FXML private Text product6Text;
    @FXML private Text product7Text;
    @FXML private Text product8Text;
    @FXML private Text product9Text;
    @FXML private Text product10Text;
    @FXML private Text product11Text;
    @FXML private Text product12Text;
    @FXML private Text product13Text;
    @FXML private Text product14Text;
    @FXML private Text product15Text;
    @FXML private Text product16Text;

    private static final String[] PRODUCT_NAMES = {
            "CICOSAT Criminology Gala Uniform (Pants)", "CICOSAT Criminology Gala Uniform (Top)",
            "DMMMSU Computer Science Departmental Uniform (Top)", "DMMMSU Computer Science Departmental Uniform (Pants)",
            "Lorma Colleges Institutional Uniform", "Lorma Colleges PE Uniform",
            "Lorma Colleges Nursing Departmental Uniform", "Lorma Colleges CCSE Departmental Uniform",
            "Lorma Colleges Nursing Gala Uniform (Female)", "SLC PE Shirt",
            "SLC Gala Uniform (Female - Skirt)", "SLC Gala Uniform (Female - Top)",
            "SLC CSA Departmental Uniform", "SLC CEA Departmental Uniform",
            "DMMMSU Pathfit Uniform", "SLC NSTP Uniform"
    };

    private static final String[] IMAGE_PATHS = {
            "CICOSAT Criminology Gala Uniform (Pants).jpg", "CICOSAT Criminology Gala Uniform (Top).jpg",
            "DMMMSU Computer Science Departmental Uniform (Top).jpg", "DMMMSU Computer Science Departmental Uniform (Pants).jpg",
            "Lorma Colleges Institutional Uniform.jpg", "Lorma Colleges PE Uniform.jpg",
            "Lorma Colleges Nursing Departmental Uniform.jpg", "Lorma Colleges CCSE Departmental Uniform.jpg",
            "Lorma Colleges Nursing Gala Uniform (Female).jpg", "SLC PE Shirt.jpg",
            "SLC Gala Uniform (Female - Skirt).jpg", "SLC Gala Uniform (Female - Top).jpg",
            "SLC CSA Departmental Uniform.jpg", "SLC CEA Departmental Uniform.jpg",
            "DMMMSU Pathfit Uniform.jpg", "SLC NSTP Uniform.jpg"
    };

    private static final double[] PRICES = {
            250, 450, 300, 200, 250, 200, 300, 300, 500, 250, 250, 300, 300, 300, 250, 250
    };

    // Method to load product data into the FXML components
    public void initialize() {
        loadProduct(0, product1ImageView, product1Text);
        loadProduct(1, product2ImageView, product2Text);
        loadProduct(2, product3ImageView, product3Text);
        loadProduct(3, product4ImageView, product4Text);
        loadProduct(4, product5ImageView, product5Text);
        loadProduct(5, product6ImageView, product6Text);
        loadProduct(6, product7ImageView, product7Text);
        loadProduct(7, product8ImageView, product8Text);
        loadProduct(8, product9ImageView, product9Text);
        loadProduct(9, product10ImageView, product10Text);
        loadProduct(10, product11ImageView, product11Text);
        loadProduct(11, product12ImageView, product12Text);
        loadProduct(12, product13ImageView, product13Text);
        loadProduct(13, product14ImageView, product14Text);
        loadProduct(14, product15ImageView, product15Text);
        loadProduct(15, product16ImageView, product16Text);
    }

    // Helper method to load a product into an ImageView and Text
    private void loadProduct(int index, ImageView imageView, Text text) {
        String imagePath = "/images/" + IMAGE_PATHS[index];
        imageView.setImage(new Image(getClass().getResourceAsStream(imagePath)));
        text.setText(PRODUCT_NAMES[index] + "\n₱" + PRICES[index]);

        // Set action for clicking the image
        imageView.setOnMouseClicked(event -> showProductDetails(index));
    }

    // Show product details and navigate to ProductPreview.fxml
    private void showProductDetails(int index) {
        try {
            // Assuming that you are passing productID instead of index
            int productID = index + 1; // Adjust if necessary to pass correct productID

            // Ensure the product ID is within bounds of your array
            if (productID < 1 || productID > PRODUCT_NAMES.length) {
                throw new IllegalArgumentException("Product ID out of bounds");
            }

            // Load the ProductPreview screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/productPreview.fxml"));
            Parent root = loader.load();

            // Pass product data to ProductPreviewController
            ProductPreviewController controller = loader.getController();
            controller.initialize(productID);  // Pass the actual productID

            Stage stage = (Stage) product1ImageView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Product Preview");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/purchaseManage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Buyer Section");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Navigating to Buyer screen...");
    }

    @FXML
    private void viewMessages(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/messages.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Buyer - Messages");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Navigating to Buyer screen...");
    }

    @FXML
    private void viewOrders(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/orders.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Buyer - Orders");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Navigating to Buyer screen...");
    }

    @FXML
    private void viewCart(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cart.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Buyer - Cart");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Navigating to Buyer screen...");
    }

}
