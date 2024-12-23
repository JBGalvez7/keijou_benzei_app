package com.kb_app.keijou_benzei_app.utility;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class Database {
    private Connection connection;

    public Database(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + Config.DB_HOST + ":" + Config.DB_PORT + "/" + Config.DB_NAME, Config.DB_USER, Config.DB_PASS);
            System.out.println("Connection success...");
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close(){
        if (connection != null){
            try{
                connection.close();
                System.out.println("Disconnected from the database");
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void generateUser(String username, String password){
        String checkQuery = "SELECT COUNT(*) FROM user WHERE username = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Username already exists.");
            } else {
                String query = "INSERT INTO user(username, password) VALUES (?, ?)";
                try (PreparedStatement ps = connection.prepareStatement(query)) {
                    ps.setString(1, username);
                    ps.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
                    ps.executeUpdate();
                    System.out.println("New user created");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
