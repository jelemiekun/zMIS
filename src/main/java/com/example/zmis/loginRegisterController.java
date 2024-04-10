package com.example.zmis;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

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
    }

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
        if (event.getCode() == KeyCode.ENTER)
            proceed();
    }

    @FXML
    void textFieldEmailPressedEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            proceed();
    }

    private void proceed() {
        String email = textFieldEmail.getText().trim();
        String password = passwordFieldPassword.getText().trim();

        if (!email.isEmpty() && !password.isEmpty()) {
            if (btnProceed.getText().equals("Log in")) {
                if (SQLLogin(email, password)) {
                    System.exit(0);
                } else {
                    alertIncorrectCredentials();
                }
            } else if (btnProceed.getText().equals("Register")) {
                if (SQLRegister(email, password)) {
                    alertRegisterSuccess();
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
}
