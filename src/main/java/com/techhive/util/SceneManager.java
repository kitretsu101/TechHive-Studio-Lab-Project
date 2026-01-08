package com.techhive.util;

import com.techhive.controller.*;
import com.techhive.model.Engineer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Centralized scene management for page navigation.
 */
public class SceneManager {

    private static SceneManager instance;
    private Stage primaryStage;
    private Scene scene;
    private BorderPane rootPane;

    private SceneManager(Stage stage) {
        this.primaryStage = stage;
        this.rootPane = new BorderPane();
        this.scene = new Scene(rootPane, 1400, 900);
    }

    public static void initialize(Stage stage) {
        if (instance == null) {
            instance = new SceneManager(stage);
        }
    }

    public static SceneManager getInstance() {
        return instance;
    }

    public Scene getScene() {
        return scene;
    }

    public void navigateToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
            Parent root = loader.load();
            rootPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToEngineers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/engineers.fxml"));
            Parent root = loader.load();
            rootPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToEngineerProfile(Engineer engineer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/engineer-profile.fxml"));
            Parent root = loader.load();
            EngineerProfileController controller = loader.getController();
            controller.setEngineer(engineer);
            rootPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToPortfolio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/portfolio.fxml"));
            Parent root = loader.load();
            rootPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToServices() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/services.fxml"));
            Parent root = loader.load();
            rootPane.setCenter(root);
            rootPane.setTop(null); // Clear any extra top content if needed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToContribute() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/contribute.fxml"));
            Parent root = loader.load();
            rootPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToContact() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/contact.fxml"));
            Parent root = loader.load();
            rootPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
