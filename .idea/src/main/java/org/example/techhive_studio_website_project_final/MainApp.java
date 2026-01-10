package org.example.techhive_studio_website_project_final;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.techhive_studio_website_project_final.core.SceneManager;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Initialize SceneManager
        SceneManager.getInstance().setPrimaryStage(stage);

        stage.setTitle("TechHive Studio");

        // Load Home View
        SceneManager.getInstance().switchView("fxml/HomeView.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}
