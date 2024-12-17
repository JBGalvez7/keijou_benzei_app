module com.kb_app.keijou_benzei_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;
    requires mysql.connector.j;
    requires java.desktop;

    opens com.kb_app.keijou_benzei_app.controllers to javafx.fxml, java.base;
    exports com.kb_app.keijou_benzei_app.controllers;

    opens com.kb_app.keijou_benzei_app to javafx.fxml;
    exports com.kb_app.keijou_benzei_app;
}