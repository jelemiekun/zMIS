package com.example.zmis;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static com.example.zmis.Alerts.*;
import static com.example.zmis.SQL.*;
import static com.example.zmis.loginRegisterController.*;

public class mainController implements Initializable {
    public static String full_name = "";
    private Account account;
    private boolean accountIsApplied;
    private ObservableList<Account> accountObservableListEnrolled = FXCollections.observableArrayList();
    private ObservableList<Account> accountApplicantsObservableListEnrolled = FXCollections.observableArrayList();
    private ObservableList<Account> accountEnrolledObservableListEnrolled = FXCollections.observableArrayList();
    private ObservableList<Account> accountDeclinedObservableListEnrolled = FXCollections.observableArrayList();

    public static int openFromDashboard = 1;

    @FXML
    private HBox hBoxNavigationBar;

    @FXML
    private BorderPane borderPaneMain;

    @FXML
    private AnchorPane anchorPaneEnrollNavigation;

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
    private MFXTextField textFieldEmailAddress;

    @FXML
    private MFXTextField textFieldPhoneNumber;

    @FXML
    private MFXLegacyComboBox<String> comboBoxCivilStatus;

    @FXML
    private MFXLegacyComboBox<String> comboBoxSex;

    @FXML
    private MFXLegacyComboBox<String> comboBoxStrand;

    @FXML
    private DatePicker datePickerDateOfBirth;

    @FXML
    private Hyperlink hyperLinkWebsite;

    @FXML
    private Label labelDashboardApplicantsCounter;

    @FXML
    private Label labelDashboardDeclinedCounter;

    @FXML
    private Label labelDashboardEnrolledCounter;

    @FXML
    private TableView<Account> tableViewDashBoardApplicants;

    @FXML
    private TableColumn<Account, String> tableViewDashBoardApplicantsColumnFullName;

    @FXML
    private TableColumn<Account, String> tableViewDashBoardApplicantsColumnStatus;

    @FXML
    private TableView<Account> tableViewDashBoardDeclined;

    @FXML
    private TableColumn<Account, String> tableViewDashBoardDeclinedColumnFullName;

    @FXML
    private TableView<Account> tableViewDashBoardEnrolled;

    @FXML
    private TableColumn<Account, String> tableViewDashBoardEnrolledColumnFullName;

    @FXML
    private TableView<Account> tableViewEnrolled;

    @FXML
    private TableColumn<Account, String> tableViewEnrolledColumnFullName;

    @FXML
    private TableColumn<Account, String> tableViewEnrolledColumnSection;

    @FXML
    private TableColumn<Account, String> tableViewEnrolledColumnStrand;

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
    private VBox vBoxMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int startUpPane = isAdmin ? 1 : 2;

        if (startUpPane == 1)  {
            hBoxNavigationBar.getChildren().remove(anchorPaneEnrollNavigation);
            setDashboardContent();
        } else {
            buttonNavDashboard.setText("");
            buttonNavDashboard.setDisable(true);
            initializeStudentAccount();
        }

        setEnrolledTable();
        switchPane(startUpPane);

        Platform.runLater(() -> borderPaneMain.requestFocus());
    }

    private void setDashboardContent() {
        initializeDashboardTables();
    }

    private void initializeDashboardTables() {
        tableViewDashBoardApplicants.setItems(accountApplicantsObservableListEnrolled);
        tableViewDashBoardApplicantsColumnFullName.setReorderable(false);
        tableViewDashBoardApplicantsColumnStatus.setReorderable(false);

        tableViewDashBoardApplicantsColumnFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tableViewDashBoardApplicantsColumnStatus.setCellValueFactory(new PropertyValueFactory<>("documentStatus"));

        tableViewDashBoardEnrolled.setItems(accountEnrolledObservableListEnrolled);
        tableViewDashBoardEnrolledColumnFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        tableViewDashBoardDeclined.setItems(accountDeclinedObservableListEnrolled);
        tableViewDashBoardDeclinedColumnFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    }

    private void setDashboardCounters() {
        labelDashboardApplicantsCounter.setText(String.valueOf(tableViewDashBoardApplicants.getItems().size()));
        labelDashboardEnrolledCounter.setText(String.valueOf(tableViewDashBoardEnrolled.getItems().size()));
        labelDashboardDeclinedCounter.setText(String.valueOf(tableViewDashBoardDeclined.getItems().size()));
    }

    private void setDashboardTableContents() {
        accountApplicantsObservableListEnrolled = SQLPopulateTableViewDashboardApplicants();
        accountEnrolledObservableListEnrolled = SQLPopulateTableViewDashboardEnrolled();
        accountDeclinedObservableListEnrolled = SQLPopulateTableViewDashboardDeclined();

        tableViewDashBoardApplicants.setItems(accountApplicantsObservableListEnrolled);
        tableViewDashBoardEnrolled.setItems(accountEnrolledObservableListEnrolled);
        tableViewDashBoardDeclined.setItems(accountDeclinedObservableListEnrolled);

        tableViewDashBoardApplicants.refresh();
        tableViewDashBoardEnrolled.refresh();
        tableViewDashBoardDeclined.refresh();

        setDashboardCounters();
    }

    private void setEnrolledTable() {
        tableViewEnrolled.setItems(accountObservableListEnrolled);
        tableViewEnrolledColumnFullName.setReorderable(false);
        tableViewEnrolledColumnStrand.setReorderable(false);
        tableViewEnrolledColumnSection.setReorderable(false);

        tableViewEnrolledColumnFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tableViewEnrolledColumnStrand.setCellValueFactory(new PropertyValueFactory<>("strand"));
        tableViewEnrolledColumnSection.setCellValueFactory(new PropertyValueFactory<>("section"));

        tableViewEnrolled.setSelectionModel(null);
    }

    private void initializeStudentAccount() {
        accountIsApplied = SQLStudentAccountIsApplied();
        if (accountIsApplied) {
            account = SQLPopulateAppliedAccount();
            setAccountEnrollValues();
            disableEnrollElements();
        } else {
            setComboBoxes();
            setBirthdayPicker();
            account = new Account(referenceEmail);
        }
    }

    private void setComboBoxes() {
        ObservableList<String> observableListSex = FXCollections.observableArrayList(
                "Male", "Female"
        );
        ObservableList<String> observableListStrand = FXCollections.observableArrayList(
                "STEM", "ICT - Computer Programming", "ICT - CSS"
        );
        ObservableList<String> observableListCivilStatus = FXCollections.observableArrayList(
                "Married", "Widowed", "Separated", "Divorced", "Single"
        );

        comboBoxSex.setItems(observableListSex);
        comboBoxStrand.setItems(observableListStrand);
        comboBoxCivilStatus.setItems(observableListCivilStatus);
    }

    private void setBirthdayPicker() {
        StringConverter<LocalDate> converter = new StringConverter<>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    String month = String.format("%02d", date.getMonthValue());
                    String day = String.format("%02d", date.getDayOfMonth());
                    return month + "/" + day + "/" + date.getYear();
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        datePickerDateOfBirth.setConverter(converter);
    }



    @FXML
    void datePickerDateOfBirthOnAction() {
        setBirthdayPicker();
    }

    @FXML
    void anchorPaneContactUsRequestFocus() {
        anchorPaneContactUs.requestFocus();
    }

    @FXML
    void anchorPaneDashboardRequestFocus() {
        anchorPaneDashboard.requestFocus();
        tableViewDashBoardApplicants.getSelectionModel().clearSelection();
        tableViewDashBoardEnrolled.getSelectionModel().clearSelection();
        tableViewDashBoardDeclined.getSelectionModel().clearSelection();
    }

    @FXML
    void anchorPaneEnrollRequestFocus() {
        anchorPaneEnroll.requestFocus();
    }

    @FXML
    void anchorPaneEnrolledRequestFocus() {
        anchorPaneEnrolled.requestFocus();
    }

    @FXML
    void buttonNavLogOutOnAction() {
        if (alertConfirmLogout()) {
            try {
                goToLogin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void goToLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/zmis/loginRegister.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        Image logo = new Image(String.valueOf(getClass().getResource("/com/example/zmis/logo.png")));
        stage.getIcons().add(logo);
        stage.setScene(scene);
        stage.show();

        Stage thisStage = (Stage) buttonNavLogOut.getScene().getWindow();
        thisStage.close();
        isAdmin = false;
        referenceEmail = "";
    }

    @FXML
    void buttonImageViewSearchEnrolled() {

    }

    @FXML
    void buttonNavContactUsOnAction() {
        switchPane(4);
    }

    @FXML
    void buttonNavDashboardOnAction() {
        switchPane(1);
    }

    @FXML
    void buttonNavEnrollOnAction() {
        switchPane(2);
    }

    @FXML
    void buttonNavEnrolledOnAction() {
        switchPane(3);
    }

    @FXML
    void buttonSubmitApplicationOnAction() {
        checkFieldsBeforeSubmit();
    }

    @FXML
    void tableViewDashBoardApplicantsClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Account selectedAccount = tableViewDashBoardApplicants.getSelectionModel().getSelectedItem();
            if (selectedAccount != null) {
                full_name = selectedAccount.getFullName();
                openFromDashboard = 1;
                openFromDashboardFXML();
                tableViewDashBoardApplicants.getSelectionModel().clearSelection();
            }
        }
    }


    @FXML
    void tableViewDashBoardEnrolledClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Account selectedAccount = tableViewDashBoardEnrolled.getSelectionModel().getSelectedItem();
            if (selectedAccount != null) {
                full_name = selectedAccount.getFullName();
                openFromDashboard = 2;
                openFromDashboardFXML();
                tableViewDashBoardEnrolled.getSelectionModel().clearSelection();
            }
        }
    }


    @FXML
    void tableViewDashBoardDeclinedClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Account selectedAccount = tableViewDashBoardDeclined.getSelectionModel().getSelectedItem();
            if (selectedAccount != null) {
                full_name = selectedAccount.getFullName();
                openFromDashboard = 3;
                openFromDashboardFXML();
                tableViewDashBoardDeclined.getSelectionModel().clearSelection();
            }
        }
    }


    // Map
    private static final MapPoint mapPoint = new MapPoint(14.56378, 121.05670);
    private void createMapview() {
        Platform.runLater(() -> {
            vBoxMap.getChildren().clear();
            MapView mapView = new MapView();
            mapView.setPrefSize(600, 600);
            mapView.addLayer(new CustomMapLayer());
            mapView.setZoom(16.5);
            mapView.setCenter(mapPoint);
            vBoxMap.getChildren().add(mapView);
            VBox.setVgrow(mapView, Priority.ALWAYS);
        });
    }
    private static class CustomMapLayer extends MapLayer {
        private final Node marker;
        public CustomMapLayer() {
            marker = new Circle(7, Color.RED);
            getChildren().add(marker);
        }

        @Override
        protected  void layoutLayer() {
            Point2D point = getMapPoint(mapPoint.getLatitude(), mapPoint.getLongitude());
            marker.setTranslateX(point.getX());
            marker.setTranslateY(point.getY());
        }
    }

    private void switchPane(int paneNumber) {
        switch (paneNumber) {
            case 1: // dashboard
                buttonNavDashboard.setStyle("-fx-text-fill: #ffff00;");
                buttonNavEnroll.setStyle("-fx-text-fill: #ffffff;");
                buttonNavEnrolled.setStyle("-fx-text-fill: #ffffff");
                buttonNavContactUs.setStyle("-fx-text-fill: #ffffff");
                buttonNavLogOut.setStyle("-fx-text-fill: #ffffff");

                anchorPaneDashboard.setVisible(true);
                anchorPaneEnroll.setVisible(false);
                anchorPaneEnrolled.setVisible(false);
                anchorPaneContactUs.setVisible(false);

                setDashboardTableContents();
                break;
            case 2: // enroll
                buttonNavDashboard.setStyle("-fx-text-fill: #ffffff;");
                buttonNavEnroll.setStyle("-fx-text-fill: #ffff00;");
                buttonNavEnrolled.setStyle("-fx-text-fill: #ffffff");
                buttonNavContactUs.setStyle("-fx-text-fill: #ffffff");
                buttonNavLogOut.setStyle("-fx-text-fill: #ffffff");

                anchorPaneDashboard.setVisible(false);
                anchorPaneEnroll.setVisible(true);
                anchorPaneEnrolled.setVisible(false);
                anchorPaneContactUs.setVisible(false);

                setEnroll();
                break;
            case 3: // enrolled
                buttonNavDashboard.setStyle("-fx-text-fill: #ffffff;");
                buttonNavEnroll.setStyle("-fx-text-fill: #ffffff;");
                buttonNavEnrolled.setStyle("-fx-text-fill: #ffff00");
                buttonNavContactUs.setStyle("-fx-text-fill: #ffffff");
                buttonNavLogOut.setStyle("-fx-text-fill: #ffffff");

                anchorPaneDashboard.setVisible(false);
                anchorPaneEnroll.setVisible(false);
                anchorPaneEnrolled.setVisible(true);
                anchorPaneContactUs.setVisible(false);

                loadEnrolledTable();
                break;
            case 4: // contact us
                createMapview();
                buttonNavDashboard.setStyle("-fx-text-fill: #ffffff;");
                buttonNavEnroll.setStyle("-fx-text-fill: #ffffff;");
                buttonNavEnrolled.setStyle("-fx-text-fill: #ffffff");
                buttonNavContactUs.setStyle("-fx-text-fill: #ffff00");
                buttonNavLogOut.setStyle("-fx-text-fill: #ffffff");

                anchorPaneDashboard.setVisible(false);
                anchorPaneEnroll.setVisible(false);
                anchorPaneEnrolled.setVisible(false);
                anchorPaneContactUs.setVisible(true);
                break;
        }
    }

    private void setEnroll() {
        textFieldEmailAddress.setText(referenceEmail);
        textFieldEmailAddress.setDisable(true);
    }

    private void checkFieldsBeforeSubmit() {
        boolean proceed;

        String name = textFieldFullName.getText().trim();
        String address = textFieldHomeAddress.getText().trim();
        LocalDate birthdate = datePickerDateOfBirth.getValue();
        String sex = comboBoxSex.getValue();
        if (textFieldAge.getText().trim().isEmpty()) {
            alertSomeFieldsAreBlankOrInvalid();
        } else {
            try {
                int age = Integer.parseInt(textFieldAge.getText().trim());
                if (age <= 0) {
                    alertSomeFieldsAreBlankOrInvalid();
                } else {
                    String civilStatus = comboBoxCivilStatus.getValue();
                    String strand = comboBoxStrand.getValue();
                    String phoneNumber = textFieldPhoneNumber.getText().trim();
                    String elemSchool = textFieldElementarySchool.getText().trim();
                    String elemSchoolSY = textFieldElementarySchoolYearsAttended.getText().trim();
                    String juniorHS = textFieldJuniorHighSchool.getText().trim();
                    String juniorHSSY = textFieldJuniorHighSchoolYearsAttended.getText().trim();
                    String lrn = textFieldLRN.getText().trim();
                    boolean form137 = checkBoxForm137.isSelected();
                    boolean form138 = checkBoxForm138.isSelected();
                    boolean goodMoral = checkBoxGoodMoral.isSelected();

                    if (name.isEmpty() || address.isEmpty() || !validBirthdate(birthdate) || sex.isEmpty() ||
                            civilStatus.isEmpty() || strand.isEmpty() || phoneNumber.isEmpty() || elemSchool.isEmpty() || elemSchoolSY.isEmpty() ||
                            juniorHS.isEmpty() || juniorHSSY.isEmpty() || lrn.isEmpty()) {
                        alertSomeFieldsAreBlankOrInvalid();
                        proceed = false;
                    } else {
                        proceed = true;
                    }

                    if (proceed) {
                        SQLInsertIntoStudent(lrn, name, phoneNumber, strand, age, sex, birthdate, address, civilStatus, elemSchool, elemSchoolSY,
                                juniorHS, juniorHSSY, getDocumentStatus(form137, form138, goodMoral), form137, form138, goodMoral);
                        disableEnrollElements();
                    }
                }
            } catch (NumberFormatException e) {
                alertSomeFieldsAreBlankOrInvalid();
            }
        }
    }

    private boolean validBirthdate(LocalDate birthdate) {
        if (String.valueOf(birthdate).isEmpty())
            return false;

        if (birthdate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String formattedBirthdate = birthdate.format(formatter);

            return formattedBirthdate.matches("^\\d{2}/\\d{2}/\\d{4}$");
        } else {
            return false;
        }
    }

    private String getDocumentStatus(boolean form137, boolean form138, boolean goodMoral) {
        return form137 && form138 && goodMoral ? "Complete" : "Incomplete";
    }

    private void setAccountEnrollValues() {
        textFieldFullName.setText(account.getFullName());
        textFieldHomeAddress.setText(account.getAddress());
        datePickerDateOfBirth.setValue(account.getBirthdate());
        comboBoxSex.setValue(account.getSex());
        textFieldAge.setText(String.valueOf(account.getAge()));
        comboBoxCivilStatus.setValue(account.getCivilStatus());
        comboBoxStrand.setValue(account.getStrand());
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
        textFieldPhoneNumber.setDisable(true);
        textFieldElementarySchool.setDisable(true);
        textFieldElementarySchoolYearsAttended.setDisable(true);
        textFieldJuniorHighSchool.setDisable(true);
        textFieldJuniorHighSchoolYearsAttended.setDisable(true);
        textFieldLRN.setDisable(true);
        checkBoxForm137.setDisable(true);
        checkBoxForm138.setDisable(true);
        checkBoxGoodMoral.setDisable(true);

        buttonSubmitApplication.setDisable(true);
        buttonSubmitApplication.setText("Application Submitted");
    }

    private void loadEnrolledTable() {
        accountObservableListEnrolled = SQLPopulateTableViewEnrolled();
        tableViewEnrolled.setItems(accountObservableListEnrolled);
        tableViewEnrolled.refresh();
    }

    private void openFromDashboardFXML() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/zmis/studentInfo.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(borderPaneMain.getScene().getWindow());

        Image logo = new Image(String.valueOf(getClass().getResource("/com/example/zmis/logo.png")));
        stage.getIcons().add(logo);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();

        anchorPaneDashboardRequestFocus();
        setDashboardTableContents();
        full_name = "";
    }
}