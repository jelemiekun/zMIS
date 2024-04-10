package com.example.zmis;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class Alerts {
    public static void alertNoConnection() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Connection Error");
            alert.setContentText("Unable to connect to the database. Please check your connection and try again.");
            alert.show();
        });
    }
}
