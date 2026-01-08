package com.techhive;

import com.techhive.util.SceneManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main JavaFX Application class for TechHive Studio.
 */
public class TechHiveApp extends Application {

    private static final double WINDOW_WIDTH = 1400;
    private static final double WINDOW_HEIGHT = 900;

    @Override
    public void start(Stage primaryStage) {
        try {
            SceneManager.initialize(primaryStage);

            Scene scene = SceneManager.getInstance().getScene();
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            primaryStage.setTitle("TechHive Studio - Building Digital Excellence");
            primaryStage.setScene(scene);
            primaryStage.setWidth(WINDOW_WIDTH);
            primaryStage.setHeight(WINDOW_HEIGHT);
            primaryStage.setMinWidth(1200);
            primaryStage.setMinHeight(700);

            // Navigate to home page
            SceneManager.getInstance().navigateToHome();

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
