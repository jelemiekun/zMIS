package com.example.zmis;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import static com.example.zmis.SQL.*;

public class loginRegisterController {

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

    @FXML
    void btnLoginOnAction() {
        btnProceed.setText("Log in");
    }

    @FXML
    void btnRegisterOnAction() {
        btnProceed.setText("Register");
    }

    @FXML
    void btnProceedOnAction() {
        proceed();
    }

    @FXML
    void passwordFieldPasswordPressedEnter(KeyEvent event) {
        proceed();
    }

    @FXML
    void textFieldEmailPressedEnter(KeyEvent event) {
        proceed();
    }

    private void proceed() {
        String email = textFieldEmail.getText().trim();
        String password = passwordFieldPassword.getText().trim();

        if (!email.isEmpty() || !password.isEmpty()) {
            if (btnProceed.getText().equals("Log in")) {
                if (SQLLogin()) {
                    // proceed sa login
                } else {
                    // incorrect email or password
                }
            } else if (btnProceed.getText().equals("Register")) {
                if (SQLRegister()) {
                    // proceed sa login, alert na success ang register
                } else {
                    // ewan ko anong error pede maencounter dito? bat false
                }
            }
        } else {
            // Some fields are blank
        }
    }
}
