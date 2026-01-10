package org.example.techhive_studio_website_project_final.core;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
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

    /**
     * Switch view with a fade transition effect.
     * The current view fades out, then the new view fades in.
     */
    public void switchViewWithFade(String fxmlPath) {
        switchViewWithFade(fxmlPath, null);
    }

    /**
     * Switch view with a fade transition effect and controller setup.
     */
    public <T> void switchViewWithFade(String fxmlPath, Consumer<T> controllerSetup) {
        try {
            Parent currentRoot = primaryStage.getScene() != null ? primaryStage.getScene().getRoot() : null;

            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlPath));
            Parent newRoot = loader.load();

            if (controllerSetup != null) {
                controllerSetup.accept(loader.getController());
            }

            if (primaryStage.getScene() == null) {
                Scene scene = new Scene(newRoot);
                scene.getStylesheets()
                        .add(Objects.requireNonNull(MainApp.class.getResource("styles/theme.css")).toExternalForm());
                primaryStage.setScene(scene);
                primaryStage.show();
            } else {
                // Fade out current view, then fade in new view
                if (currentRoot != null) {
                    FadeTransition fadeOut = new FadeTransition(Duration.millis(150), currentRoot);
                    fadeOut.setFromValue(1.0);
                    fadeOut.setToValue(0.0);
                    fadeOut.setOnFinished(e -> {
                        primaryStage.getScene().setRoot(newRoot);
                        newRoot.setOpacity(0);
                        FadeTransition fadeIn = new FadeTransition(Duration.millis(150), newRoot);
                        fadeIn.setFromValue(0.0);
                        fadeIn.setToValue(1.0);
                        fadeIn.play();
                    });
                    fadeOut.play();
                } else {
                    primaryStage.getScene().setRoot(newRoot);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load view with fade: " + fxmlPath);
            e.printStackTrace();
        }
    }

    private Parent loadView(String fxmlPath) throws IOException {
        // Caching can be added here if needed, but for simplicity reloading for now
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlPath));
        return loader.load();
    }
}
