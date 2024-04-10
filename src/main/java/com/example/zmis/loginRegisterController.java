package com.example.zmis;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.zmis.Alerts.*;
import static com.example.zmis.SQL.*;

public class loginRegisterController implements Initializable {

    @FXML
    private AnchorPane anchorPaneOperation;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnProceed;

    @FXML
    private Button btnRegister;

    @FXML
    private PasswordField passwordFieldPassword;

    @FXML
    private TextField textFieldEmail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnProceed.setText("Log in");
        Platform.runLater(() -> anchorPaneOperation.requestFocus());
    }

    @FXML
    void btnLoginOnAction() {
        btnProceed.setText("Log in");
        clearFields();
    }

    @FXML
    void btnRegisterOnAction() {
        btnProceed.setText("Register");
        clearFields();
    }

    @FXML
    void btnProceedOnAction() throws IOException {
        proceed();
    }

    @FXML
    void passwordFieldPasswordPressedEnter(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER)
            proceed();
    }

    @FXML
    void textFieldEmailPressedEnter(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER)
            proceed();
    }

    private void proceed() throws IOException {
        String email = textFieldEmail.getText().trim();
        String password = passwordFieldPassword.getText().trim();

        if (!email.isEmpty() && !password.isEmpty()) {
            if (btnProceed.getText().equals("Log in")) {
                if (SQLLogin(email, password))
                    goToMain();
            } else if (btnProceed.getText().equals("Register")) {
                if (SQLRegister(email, password)) {
                    clearFields();
                    anchorPaneOperation.requestFocus();
                    btnProceed.setText("Log in");
                } else {
                    System.out.println("may mali");
                    // ewan ko anong error pede maencounter dito? bat false
                }
            }
        } else {
            alertSomeFieldsAreBlank();
        }
    }

    private void clearFields() {
        textFieldEmail.setText("");
        passwordFieldPassword.setText("");
    }

    private void goToMain() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/zmis/main.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        Stage thisStage = (Stage) anchorPaneOperation.getScene().getWindow();
        thisStage.close();
    }
}
