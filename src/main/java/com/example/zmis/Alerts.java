package com.example.zmis;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class Alerts {
    public static void alertNoConnection() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Connection Error");
        alert.setContentText("Unable to connect to the database. Please check your connection and try again.");
        alert.show();
    }

    public static void alertIncorrectCredentials() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Login Error");
        alert.setContentText("Incorrect email or password. Please try again.");
        alert.show();
    }

    public static void alertSomeFieldsAreBlank() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Fields empty");
        alert.setContentText("Please fill in all required fields.");
        alert.show();
    }

    public static void alertRegisterSuccess() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Success");
        alert.setHeaderText("Registration Successful");
        alert.setContentText("Your registration has been completed successfully. You can now log in with your credentials.");
        alert.show();
    }
}
