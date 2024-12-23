package com.kb_app.keijou_benzei_app;

import com.kb_app.keijou_benzei_app.utility.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 500);
        stage.setTitle("App Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
       // (new Database()).generateUser("admin", "admin1234");
        launch();
    }


}
