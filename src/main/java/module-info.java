module com.eiman.aeropuerto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.eiman.aeropuerto to javafx.fxml;
    exports com.eiman.aeropuerto;
    exports com.eiman.aeropuerto.controllers;
    opens com.eiman.aeropuerto.controllers to javafx.fxml;
    exports com.eiman.aeropuerto.models;
    opens com.eiman.aeropuerto.models to javafx.fxml;
}