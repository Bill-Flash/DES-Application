module com.example.des {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.apache.commons.codec;
    requires java.sql;
    requires javafx.web;
    requires mysql.connector.java;

    opens com.example.des to javafx.fxml;
    exports com.example.des;
    exports com.example.des.util;
    opens com.example.des.util to javafx.fxml;
    exports com.example.des.controller;
    opens com.example.des.controller to javafx.fxml;
    exports com.example.des.model;
    opens com.example.des.model to javafx.fxml;
}