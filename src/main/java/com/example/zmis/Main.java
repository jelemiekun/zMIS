package com.example.zmis;

import io.github.palexdev.materialfx.theming.JavaFXThemes;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        setStyleSheets();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("zMIS");
        Image logo = new Image(String.valueOf(getClass().getResource("/com/example/zmis/logo.png")));
        stage.getIcons().add(logo);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void setStyleSheets() {
        UserAgentBuilder.builder()
                .themes(JavaFXThemes.MODENA) // Optional if you don't need JavaFX's default theme, still recommended though
                .themes(MaterialFXStylesheets.forAssemble(true)) // Adds the MaterialFX's default theme. The boolean argument is to include legacy controls
                .setDeploy(true) // Whether to deploy each theme's assets on a temporary dir on the disk
                .setResolveAssets(true) // Whether to try resolving @import statements and resources urls
                .build() // Assembles all the added themes into a single CSSFragment (very powerful class check its documentation)
                .setGlobal(); // Finally, sets the produced stylesheet as the global User-Agent stylesheet
    }

    public static void main(String[] args) {
        launch();
    }
}