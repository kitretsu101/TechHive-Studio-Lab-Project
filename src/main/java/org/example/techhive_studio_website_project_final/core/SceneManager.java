package org.example.techhive_studio_website_project_final.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.techhive_studio_website_project_final.MainApp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class SceneManager {
    private static SceneManager instance;
    private Stage primaryStage;
    private final Map<String, Parent> viewCache = new HashMap<>();

    private SceneManager() {
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void switchView(String fxmlPath) {
        switchView(fxmlPath, null);
    }

    public <T> void switchView(String fxmlPath, Consumer<T> controllerSetup) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlPath));
            Parent root = loader.load();

            if (controllerSetup != null) {
                controllerSetup.accept(loader.getController());
            }

            if (primaryStage.getScene() == null) {
                Scene scene = new Scene(root);
                // Apply theme globally
                scene.getStylesheets()
                        .add(Objects.requireNonNull(MainApp.class.getResource("styles/theme.css")).toExternalForm());
                primaryStage.setScene(scene);
            } else {
                primaryStage.getScene().setRoot(root);
            }
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Failed to load view: " + fxmlPath);
            e.printStackTrace();
        }
    }

    private Parent loadView(String fxmlPath) throws IOException {
        // Caching can be added here if needed, but for simplicity reloading for now
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlPath));
        return loader.load();
    }
}
