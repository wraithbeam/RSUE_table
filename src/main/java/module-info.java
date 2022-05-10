module com.example.rsue {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    requires org.apache.poi.ooxml.schemas;
    requires org.apache.poi.ooxml;
    requires org.apache.poi.poi;

    requires org.apache.logging.log4j;
    requires org.controlsfx.controls;

    opens com.example.rsue to javafx.fxml;
    exports com.example.rsue;
    exports com.example.rsue.controllers;
    opens com.example.rsue.controllers to javafx.fxml;
}