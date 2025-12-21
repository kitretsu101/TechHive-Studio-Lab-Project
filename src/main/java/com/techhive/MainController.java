package com.techhive;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane mainLayout;

    @FXML
    public void initialize() {
        showHome();
    }

    @FXML
    private void showHome() {
        loadView("home-view.fxml");
    }

    @FXML
    private void showServices() {
        loadView("services-view.fxml");
    }

    @FXML
    private void showPortfolio() {
        loadView("portfolio-view.fxml");
    }

    @FXML
    private void showEngineers() {
        loadView("engineers-view.fxml");
    }

    @FXML
    private void showContact() {
        loadView("contact-view.fxml");
    }

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent view = loader.load();
            mainLayout.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
