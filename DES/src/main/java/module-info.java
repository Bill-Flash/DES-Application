module com.example.des {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.des to javafx.fxml;
    exports com.example.des;
    exports com.example.des.util;
    opens com.example.des.util to javafx.fxml;
    exports com.example.des.controller;
    opens com.example.des.controller to javafx.fxml;
    exports com.example.des.application;
    opens com.example.des.application to javafx.fxml;
    exports com.example.des.en;
    opens com.example.des.en to javafx.fxml;
}