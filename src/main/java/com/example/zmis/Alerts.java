package com.example.zmis;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

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

    public static void alertRegisterPasswordMismatch() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Incorrect password");
        alert.setContentText("New password and confirm new password does not match. Please try again.");
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Registration Successful");
        alert.setContentText("Your registration has been completed successfully. You can now log in with your credentials.");
        alert.showAndWait();
    }

    public static void alertRegisterEmailExists() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Duplicate email registration");
        alert.setContentText("Email already registered. Please use a different email.");
        alert.show();
    }

    public static boolean alertConfirmLogout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Confirm log-out?");
        alert.setContentText("Select 'yes' to log-out and 'no' to stay logged-in.");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            return true; // clicked 'yes'
        } else {
            return false; // clicked 'no'
        }
    }

    public static void alertSQLRandomError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An unexpected error occurred.");
        alert.setContentText("An unexpected error occurred. Please try again later.");
        alert.show();
    }

    public static void alertSomeFieldsAreBlankOrInvalid() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Application Incomplete/Invalid");
        alert.setContentText("Please fill in all required fields with valid inputs.");
        alert.show();
    }

    public static boolean alertConfirmSubmit() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Confirm Submission?");
        alert.setContentText("Select 'yes' to submit and 'cancel' to cancel.");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeYes;
    }


    public static boolean alertConfirmOngoing() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Confirm process?");
        alert.setContentText("Select 'yes' to continue and 'no' to cancel.");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeYes;
    }
}
