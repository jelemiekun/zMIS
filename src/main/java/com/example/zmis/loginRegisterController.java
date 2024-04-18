package com.example.zmis;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.image.Image;
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
    public static boolean isAdmin = false;
    public static String referenceEmail = "";

    @FXML
    private AnchorPane anchorPaneOperation;

    @FXML
    private AnchorPane anchorPaneLogin;

    @FXML
    private AnchorPane anchorPaneRegister;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXButton btnProceed;

    @FXML
    private JFXButton btnRegister;

    @FXML
    private PasswordField passwordFieldPassword;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldEmail1;

    @FXML
    private PasswordField passwordFieldPassword1;

    @FXML
    private PasswordField passwordFieldPassword11;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() ->  {
            anchorPaneOperation.requestFocus();
            btnLoginOnAction();
        });
    }

    @FXML
    void btnLoginOnAction() {
        btnProceed.setText("Log in");
        btnRegister.setStyle("-fx-background-color: #bcbcbc");
        btnLogin.setStyle("-fx-background-color: #151515;");
        anchorPaneRegister.setVisible(false);
        anchorPaneLogin.setVisible(true);
        clearFields();
        anchorPaneOperation.requestFocus();
    }

    @FXML
    void btnRegisterOnAction() {
        btnProceed.setText("Register");
        btnLogin.setStyle("-fx-background-color: #bcbcbc");
        btnRegister.setStyle("-fx-background-color: #151515;");
        anchorPaneRegister.setVisible(true);
        anchorPaneLogin.setVisible(false);
        clearFields();
        anchorPaneOperation.requestFocus();
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
        if (btnProceed.getText().equals("Log in")) {
            System.out.println("login");
            String email = textFieldEmail.getText().trim();
            String password = passwordFieldPassword.getText().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                if (btnProceed.getText().equals("Log in")) {
                    if (SQLLogin(email, password))
                        goToMain();
                    else
                        alertIncorrectCredentials();
                }
            } else {
                alertSomeFieldsAreBlank();
            }
        } else if (btnProceed.getText().equals("Register")) {
            System.out.println("register");

                String emailRegister = textFieldEmail1.getText().trim();
                String newPassword = passwordFieldPassword1.getText().trim();
                String confirmNewPassword = passwordFieldPassword11.getText().trim();

                if (!emailRegister.isEmpty() && !newPassword.isEmpty() && !confirmNewPassword.isEmpty()) {
                    if (newPassword.equals(confirmNewPassword)) {
                        if (SQLRegister(emailRegister, newPassword)) {
                            btnLoginOnAction();
                        } else {
                            System.out.println("incorrect");
                        }
                    } else {
                        alertRegisterPasswordMismatch();
                    }
                } else {
                    alertSomeFieldsAreBlank();
                }
        }
    }

    private void clearFields() {
        textFieldEmail.setText("");
        passwordFieldPassword.setText("");

        textFieldEmail1.setText("");
        passwordFieldPassword1.setText("");
        passwordFieldPassword11.setText("");
    }

    private void goToMain() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/zmis/main.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        Image logo = new Image(String.valueOf(getClass().getResource("/com/example/zmis/logo.png")));
        stage.getIcons().add(logo);
        stage.setScene(scene);
        stage.show();

        Stage thisStage = (Stage) anchorPaneOperation.getScene().getWindow();
        thisStage.close();
    }
}
