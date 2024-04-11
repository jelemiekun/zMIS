package com.example.zmis;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class mainController {

    @FXML
    private AnchorPane anchorPaneContactUs;

    @FXML
    private AnchorPane anchorPaneDashboard;

    @FXML
    private AnchorPane anchorPaneEnroll;

    @FXML
    private AnchorPane anchorPaneEnrolled;

    @FXML
    private JFXButton buttonNavContactUs;

    @FXML
    private JFXButton buttonNavDashboard;

    @FXML
    private JFXButton buttonNavEnroll;

    @FXML
    private JFXButton buttonNavEnrolled;

    @FXML
    private JFXButton buttonNavLogOut;

    @FXML
    private MFXButton buttonSubmitApplication;

    @FXML
    private JFXCheckBox checkBoxForm137;

    @FXML
    private JFXCheckBox checkBoxForm138;

    @FXML
    private JFXCheckBox checkBoxGoodMoral;

    @FXML
    private MFXLegacyComboBox<?> comboBoxCivilStatus;

    @FXML
    private MFXTextField comboBoxEmailAddress;

    @FXML
    private MFXTextField comboBoxPhoneNumber;

    @FXML
    private MFXLegacyComboBox<?> comboBoxSex;

    @FXML
    private MFXLegacyComboBox<?> comboBoxStrand;

    @FXML
    private DatePicker datePickerDateOfBirth;

    @FXML
    private Label labelDashboardApplicantsCounter;

    @FXML
    private Label labelDashboardDeclinedCounter;

    @FXML
    private Label labelDashboardEnrolledCounter;

    @FXML
    private TableView<?> tableViewDashBoardApplicants;

    @FXML
    private TableColumn<?, ?> tableViewDashBoardApplicantsColumnFullName;

    @FXML
    private TableColumn<?, ?> tableViewDashBoardApplicantsColumnStatus;

    @FXML
    private TableView<?> tableViewDashBoardDeclined;

    @FXML
    private TableColumn<?, ?> tableViewDashBoardDeclinedColumnFullName;

    @FXML
    private TableView<?> tableViewDashBoardEnrolled;

    @FXML
    private TableColumn<?, ?> tableViewDashBoardEnrolledColumnFullName;

    @FXML
    private TableView<?> tableViewEnrolled;

    @FXML
    private TableColumn<?, ?> tableViewEnrolledColumnFullName;

    @FXML
    private TableColumn<?, ?> tableViewEnrolledColumnSection;

    @FXML
    private TableColumn<?, ?> tableViewEnrolledColumnStrand;

    @FXML
    private MFXTextField textFieldAge;

    @FXML
    private MFXTextField textFieldElementarySchool;

    @FXML
    private MFXTextField textFieldElementarySchoolYearsAttended;

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
    private MFXTextField textFieldSearchEnrolled;

    @FXML
    void anchorPaneContactUsRequestFocus(MouseEvent event) {

    }

    @FXML
    void anchorPaneDashboardRequestFocus(MouseEvent event) {

    }

    @FXML
    void anchorPaneEnrollRequestFocus(MouseEvent event) {

    }

    @FXML
    void anchorPaneEnrolledRequestFocus(MouseEvent event) {

    }

    @FXML
    void buttonImageViewSearchEnrolled(MouseEvent event) {

    }

    @FXML
    void buttonNavContactUsOnAction(ActionEvent event) {

    }

    @FXML
    void buttonNavDashboardOnAction(ActionEvent event) {

    }

    @FXML
    void buttonNavEnrollOnAction(ActionEvent event) {

    }

    @FXML
    void buttonNavEnrolledOnAction(ActionEvent event) {

    }

    @FXML
    void buttonNavLogOutOnAction(ActionEvent event) {

    }

    @FXML
    void buttonSubmitApplicationOnAction(ActionEvent event) {

    }

    @FXML
    void checkBoxEnrollOnAction(ActionEvent event) {

    }

    @FXML
    void comboBoxCivilStatusOnAction(ActionEvent event) {

    }

    @FXML
    void comboBoxSexOnAction(ActionEvent event) {

    }

    @FXML
    void comboBoxStrandOnAction(ActionEvent event) {

    }

    @FXML
    void tableViewDashBoardApplicantsClicked(MouseEvent event) {

    }

    @FXML
    void tableViewDashBoardDeclinedClicked(MouseEvent event) {

    }

    @FXML
    void tableViewDashBoardEnrolledClicked(MouseEvent event) {

    }

}
