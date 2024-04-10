package com.example.zmis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(SQL::SQLCreateConnection);
        executor.shutdown();


        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginRegister.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("zMIS");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}