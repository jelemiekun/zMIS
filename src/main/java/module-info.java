module com.example.zmis {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.zaxxer.hikari;
    requires com.jfoenix;
    requires MaterialFX;


    opens com.example.zmis to javafx.fxml;
    exports com.example.zmis;
}