package com.example.zmis;

import com.jfoenix.controls.JFXCheckBox;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.zmis.SQL.*;
import static com.example.zmis.mainController.*;

public class studentInfoController implements Initializable {
    private Account account;

    @FXML
    private AnchorPane anchorPaneEnroll;

    @FXML
    private MFXButton buttonAcceptApplication;

    @FXML
    private MFXButton buttonDeclineApplication;

    @FXML
    private JFXCheckBox checkBoxForm137;

    @FXML
    private JFXCheckBox checkBoxForm138;

    @FXML
    private JFXCheckBox checkBoxGoodMoral;

    @FXML
    private MFXLegacyComboBox<String> comboBoxCivilStatus;

    @FXML
    private MFXLegacyComboBox<String> comboBoxSex;

    @FXML
    private MFXLegacyComboBox<String> comboBoxStrand;

    @FXML
    private DatePicker datePickerDateOfBirth;

    @FXML
    private FlowPane flowPaneButtons;

    @FXML
    private Label labelApplicationStatus;

    @FXML
    private MFXTextField textFieldAge;

    @FXML
    private MFXTextField textFieldElementarySchool;

    @FXML
    private MFXTextField textFieldElementarySchoolYearsAttended;

    @FXML
    private MFXTextField textFieldEmailAddress;

    @FXML
    private MFXTextField textFieldFullName;

    @FXML
    private MFXTextField textFieldHomeAddress;

    @FXML
    private MFXTextField textFieldJuniorHighSchool;

    @FXML
    private MFXTextField textFieldJuniorHighSchoolYearsAttended;

    @FXML
    private MFXTextField textFieldLRN;

    @FXML
    private MFXTextField textFieldPhoneNumber;

    @FXML
    void anchorPaneEnrollRequestFocus(MouseEvent event) {

    }

    @FXML
    void buttonAcceptApplicationOnAction(ActionEvent event) {

    }

    @FXML
    void buttonDeclineApplicationOnAction(ActionEvent event) {

    }

    @FXML
    void datePickerDateOfBirthOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getAccount();
        setAccountEnrollValues();
        disableEnrollElements();

        switch (openFromDashboard) {
            case 1: // ongoing applicant
                break;
            case 2: // enrolled applicant
                break;
            case 3: // declined applicant
                break;
        }
    }

    private void getAccount() {
        account = SQLPopulateApplicationSelected();
    }

    private void setAccountEnrollValues() {

        textFieldFullName.setText(account.getFullName());
        textFieldHomeAddress.setText(account.getAddress());
        datePickerDateOfBirth.setValue(account.getBirthdate());
        comboBoxSex.setValue(account.getSex());
        textFieldAge.setText(String.valueOf(account.getAge()));
        comboBoxCivilStatus.setValue(account.getCivilStatus());
        comboBoxStrand.setValue(account.getStrand());
        textFieldEmailAddress.setText(account.getEmail());
        textFieldPhoneNumber.setText(account.getPhoneNumber());
        textFieldElementarySchool.setText(account.getElemSchool());
        textFieldJuniorHighSchool.setText(account.getJuniorHS());
        textFieldJuniorHighSchoolYearsAttended.setText(account.getJuniorHSSY());
        textFieldLRN.setText(account.getLRN());
        checkBoxForm137.setSelected(account.isForm137());
        checkBoxForm138.setSelected(account.isForm138());
        checkBoxGoodMoral.setSelected(account.isGoodMoral());
    }

    private void disableEnrollElements() {
        textFieldFullName.setDisable(true);
        textFieldHomeAddress.setDisable(true);
        datePickerDateOfBirth.setDisable(true);
        comboBoxSex.setDisable(true);
        textFieldAge.setDisable(true);
        comboBoxCivilStatus.setDisable(true);
        comboBoxStrand.setDisable(true);
        textFieldEmailAddress.setDisable(true);
        textFieldPhoneNumber.setDisable(true);
        textFieldElementarySchool.setDisable(true);
        textFieldElementarySchoolYearsAttended.setDisable(true);
        textFieldJuniorHighSchool.setDisable(true);
        textFieldJuniorHighSchoolYearsAttended.setDisable(true);
        textFieldLRN.setDisable(true);
    }
}
