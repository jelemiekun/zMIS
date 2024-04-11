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
import javafx.event.ActionEvent;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.zmis.Alerts.*;
import static com.example.zmis.loginRegisterController.*;

public class mainController implements Initializable {

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
    private Hyperlink hyperLinkWebsite;

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
    private VBox vBoxMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int startUpPane = isAdmin ? 1 : 2;

        if (startUpPane == 1)  {
            hBoxNavigationBar.getChildren().remove(anchorPaneEnrollNavigation);
        } else {
            buttonNavDashboard.setText("");
            buttonNavDashboard.setDisable(true);
        }

        switchPane(startUpPane);

        Platform.runLater(() -> borderPaneMain.requestFocus());
    }

    @FXML
    void anchorPaneContactUsRequestFocus() {
        anchorPaneContactUs.requestFocus();
    }

    @FXML
    void anchorPaneDashboardRequestFocus() {
        anchorPaneDashboard.requestFocus();
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
        comboBoxEmailAddress.setText(referenceEmail);
        comboBoxEmailAddress.setDisable(true);
    }
}