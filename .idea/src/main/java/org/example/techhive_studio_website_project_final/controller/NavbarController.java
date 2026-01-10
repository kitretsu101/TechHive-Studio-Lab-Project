package org.example.techhive_studio_website_project_final.controller;

import javafx.fxml.FXML;
import org.example.techhive_studio_website_project_final.core.SceneManager;

public class NavbarController {

    @FXML
    private void onHomeClick() {
        SceneManager.getInstance().switchView("fxml/HomeView.fxml");
    }

    @FXML
    private void onServicesClick() {
        SceneManager.getInstance().switchView("fxml/ServicesView.fxml");
    }

    @FXML
    private void onPortfolioClick() {
        SceneManager.getInstance().switchView("fxml/PortfolioView.fxml");
    }

    @FXML
    private void onEngineersClick() {
        SceneManager.getInstance().switchViewWithFade("fxml/EngineersView.fxml");
    }

    @FXML
    private void onContributeClick() {
        SceneManager.getInstance().switchView("fxml/ContributeView.fxml");
    }

    @FXML
    private void onContactClick() {
        SceneManager.getInstance().switchView("fxml/ContactView.fxml");
    }

    @FXML
    private void onStartProjectClick() {
        SceneManager.getInstance().switchView("fxml/ContactView.fxml");
    }

    @FXML
    private void onLoginClick() {
        SceneManager.getInstance().switchViewWithFade("fxml/LoginView.fxml");
    }
}
