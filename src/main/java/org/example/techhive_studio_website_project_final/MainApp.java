package org.example.techhive_studio_website_project_final;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.techhive_studio_website_project_final.core.SceneManager;
import org.example.techhive_studio_website_project_final.data.DatabaseManager;
import org.example.techhive_studio_website_project_final.model.UserSession;
import org.example.techhive_studio_website_project_final.service.FirebaseAuthService;
import org.example.techhive_studio_website_project_final.service.FirestoreService;

import java.io.IOException;

/**
 * Main application entry point.
 * Initializes Firebase, SQLite, and checks for existing session.
 */
public class MainApp extends Application {

    @Override
    public void init() {
        // Initialize Firebase services
        FirebaseAuthService.getInstance().initialize();
        FirestoreService.getInstance().initialize();

        // Initialize SQLite database
        DatabaseManager.getInstance().initializeDatabase();

        System.out.println("TechHive Studio initialized.");
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Initialize SceneManager
        SceneManager.getInstance().setPrimaryStage(stage);

        stage.setTitle("TechHive Studio");

        // Check for existing session (Remember Me)
        if (UserSession.getInstance().loadSession()) {
            System.out.println("Auto-login: User session restored.");
            // User was remembered, go directly to home
            SceneManager.getInstance().switchView("fxml/HomeView.fxml");
        } else {
            // No saved session, load Home View normally
            SceneManager.getInstance().switchView("fxml/HomeView.fxml");
        }
    }

    @Override
    public void stop() {
        // Clean up resources
        DatabaseManager.getInstance().closeConnection();
        System.out.println("TechHive Studio closed.");
    }

    public static void main(String[] args) {
        launch();
    }
}
