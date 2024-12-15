package com.kb_app.keijou_benzei_app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class BuyerController {

    @FXML
    private ImageView product1ImageView;
    @FXML
    private ImageView product2ImageView;
    @FXML
    private ImageView product3ImageView;
    @FXML
    private ImageView product4ImageView;
    @FXML
    private ImageView product5ImageView;
    @FXML
    private ImageView product6ImageView;
    @FXML
    private ImageView product7ImageView;
    @FXML
    private ImageView product8ImageView;
    @FXML
    private ImageView product9ImageView;
    @FXML
    private ImageView product10ImageView;

    private final Map<Integer, Product> productMap = new HashMap<>();

    @FXML
    public void initialize() {
        loadProductData();

        setupProductImages();
    }

    private void loadProductData() {
        productMap.put(1, new Product(1, "Korean Style Silk Short Sleeves button mens shirt", "150.00", "Men's Wear", "M", "Unisex", "Adult", "images/Korean Style Silk Short Sleeves button mens shirt.jpg"));
        productMap.put(2, new Product(2, "Women Korean Black Coat or Blazer", "120.00", "Women's Wear", "M", "F", "Young Adult", "images/WOMEN KOREAN BLACK COAT or BLAZER.jpg"));
        productMap.put(3, new Product(3, "Womens Graphic Oversized Crewneck Sweatshirt", "180.00", "Casual Wear", "F", "F", "Teen", "images/Womens Graphic Oversized Crewneck Long Sleeve Casual Loose Pullover Los Angeles California Vintage Sweatshirts Coffee Brown Plus Size.jpg"));
        productMap.put(4, new Product(4, "Navy Blue Shirt Women New Autumn High End Temperament V Neck", "150.00", "Formal Wear", "M", "F", "Adult", "images/Navy Blue Shirt Women New Autumn High End Temperament V Neck.jpg"));
        productMap.put(5, new Product(5, "Original Design Blue V Neck Cross Patchwork Shirt", "170.00", "Formal Wear", "S", "F", "Young Adult", "images/5.12Original Design Blue V Neck Cross Patchwork Cold Shoulder Cotton Shirts Spring.jpg"));
        productMap.put(6, new Product(6, "Vertical Knitted Short Sleeve Collar Shirt", "130.00", "Men's Wear", "M", "M", "Young Adult", "images/VERTICAL KNITTED SHORT SLEEVE COLLAR SHIRT.jpg"));
        productMap.put(7, new Product(7, "Oversized Multi Pocket Half Shirt", "130.00", "Casual Wear", "M", "Unisex", "Young Adult", "images/Oversized Multi Pocket Half Shirt.jpg"));
        productMap.put(8, new Product(8, "Young Girl Plaid Print Belted Dress", "150.00", "Dresses", "F", "F", "Teen", "images/Young Girl Plaid Print Belted Dress.jpg"));
        productMap.put(9, new Product(9, "Mens Striped Polo Shirt", "140.00", "Men's Wear", "M", "M", "Young Adult", "images/Mens Striped Polo Shirt Short Sleeve Summer Lapel Ice Silk Korean-style T-Shirt.jpg"));
        productMap.put(10, new Product(10, "Young Boy Casual Crown Pattern Polo Shirt", "80.00", "Kids Wear", "S", "M", "Toddler", "images/1pc Young Boy Casual College Style Crown Pattern Long Sleeve Polo Shirt.jpg"));
    }

    private void setupProductImages() {
        setupImageView(product1ImageView, 1);
        setupImageView(product2ImageView, 2);
        setupImageView(product3ImageView, 3);
        setupImageView(product4ImageView, 4);
        setupImageView(product5ImageView, 5);
        setupImageView(product6ImageView, 6);
        setupImageView(product7ImageView, 7);
        setupImageView(product8ImageView, 8);
        setupImageView(product9ImageView, 9);
        setupImageView(product10ImageView, 10);
    }

    private void setupImageView(ImageView imageView, int productId) {
        Product product = productMap.get(productId);
        if (product != null) {
            imageView.setImage(loadImage(product.getImagePath()));
            imageView.setOnMouseClicked(event -> handleProductClick(product));
        }
    }

    private Image loadImage(String relativePath) {
        InputStream imageStream = getClass().getResourceAsStream("/" + relativePath);
        if (imageStream != null) {
            return new Image(imageStream);
        } else {
            System.err.println("Image not found: " + relativePath);
            return new Image(getClass().getResource("/images/default_placeholder.jpg").toExternalForm());
        }
    }

    private void handleProductClick(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/productPreview.fxml"));
            Parent root = loader.load();

            ProductPreviewController controller = loader.getController();
            controller.setProductDetails(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getCategory(),
                    product.getSize(),
                    product.getGender(),
                    product.getAgePreference(),
                    product.getImagePath()
            );

            Stage stage = (Stage) product1ImageView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Product Preview");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        navigateToScene(event, "/fxml/buyerseller.fxml", "Buyer/Seller Selection");
    }

    @FXML
    private void viewOrders(ActionEvent event) {
        navigateToScene(event, "/fxml/orders.fxml", "Buyer - Orders");
    }

    @FXML
    private void viewMessages(ActionEvent event) {
        navigateToScene(event, "/fxml/messages.fxml", "Buyer - Messages");
    }

    @FXML
    private void browseProducts(ActionEvent event) {
        navigateToScene(event, "/fxml/buyer.fxml", "Buyer Section");
    }

    private void navigateToScene(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Product {
        private final int id;
        private final String name;
        private final String price;
        private final String category;
        private final String size;
        private final String gender;
        private final String agePreference;
        private final String imagePath;

        public Product(int id, String name, String price, String category, String size, String gender, String agePreference, String imagePath) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.category = category;
            this.size = size;
            this.gender = gender;
            this.agePreference = agePreference;
            this.imagePath = imagePath;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

        public String getCategory() {
            return category;
        }

        public String getSize() {
            return size;
        }

        public String getGender() {
            return gender;
        }

        public String getAgePreference() {
            return agePreference;
        }

        public String getImagePath() {
            return imagePath;
        }
    }
}
