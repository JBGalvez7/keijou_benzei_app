package com.kb_app.keijou_benzei_app.models;

import javafx.beans.property.*;

public class Product {

    private final IntegerProperty productID;
    private final StringProperty productName;
    private final DoubleProperty price;
    private final StringProperty picture;

    public Product(int productID, String productName, double price, String picture) {
        this.productID = new SimpleIntegerProperty(productID);
        this.productName = new SimpleStringProperty(productName);
        this.price = new SimpleDoubleProperty(price);
        this.picture = new SimpleStringProperty(picture);
    }


    public int getProductID() {
        return productID.get();
    }

    public IntegerProperty productIDProperty() {
        return productID;
    }

    public String getProductName() {
        return productName.get();
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public String getPicture() {
        return picture.get();
    }

    public StringProperty pictureProperty() {
        return picture;
    }
}
