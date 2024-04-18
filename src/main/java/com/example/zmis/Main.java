package com.example.zmis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.zmis.SQL.*;
import static com.example.zmis.mainController.*;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        createConnection();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginRegister.fxml"));
        setStyleSheets();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("zMIS");
        Image logo = new Image(String.valueOf(getClass().getResource("/com/example/zmis/logo.png")));
        stage.getIcons().add(logo);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}