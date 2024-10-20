module com.hrapp {
    exports com.hrapp;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.swing;
    requires java.desktop; // Required for javax.swing and other desktop classes
    requires java.sql;          // For JDBC
    requires java.net.http;


    opens com.hrapp to javafx.fxml;
}