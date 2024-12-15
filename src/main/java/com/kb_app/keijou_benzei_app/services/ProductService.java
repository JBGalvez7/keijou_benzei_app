package com.kb_app.keijou_benzei_app.services;

import com.kb_app.keijou_benzei_app.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String DB_USER = "your_user";
    private static final String DB_PASSWORD = "your_password";

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getInt("productID"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setPictureUrl(rs.getString("picture_url"));
                product.setCategory(rs.getString("category"));
                product.setStyle(rs.getString("style"));
                product.setSize(rs.getString("size"));
                product.setAgePreference(rs.getString("agePreference"));
                product.setGender(rs.getString("gender"));
                product.setRatings(rs.getDouble("ratings"));
                product.setSoldCount(rs.getInt("soldCount"));
                product.setSellerID(rs.getInt("sellerID"));

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
}
