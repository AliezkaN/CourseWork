module com.example.aircompany {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.java;

    opens com.nahorniak.aircompany.entities to javafx.base;
    opens com.nahorniak.aircompany to javafx.fxml;
    exports com.nahorniak.aircompany;
}