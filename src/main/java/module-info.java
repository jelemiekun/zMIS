module com.example.zmis {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.zmis to javafx.fxml;
    exports com.example.zmis;
}