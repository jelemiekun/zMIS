module com.example.zmis {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.zmis to javafx.fxml;
    exports com.example.zmis;
}