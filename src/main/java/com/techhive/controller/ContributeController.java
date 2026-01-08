package com.techhive.controller;

import com.techhive.util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ContributeController implements Initializable {

    @FXML
    private Label logoLabel;
    @FXML
    private HBox navLinks;
    @FXML
    private Button startProjectBtn;
    @FXML
    private Button viewOpeningsBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupNavigation();
    }

    private void setupNavigation() {
        logoLabel.setOnMouseClicked(e -> SceneManager.getInstance().navigateToHome());

        String[] navItems = { "Home", "Services", "Portfolio", "Our Engineers", "Contribute", "Contact" };
        navLinks.getChildren().clear();

        for (String item : navItems) {
            Label link = new Label(item);
            link.getStyleClass().add("nav-link");
            if (item.equals("Contribute")) {
                link.getStyleClass().add("nav-link-active");
            }
            link.setOnMouseClicked(e -> handleNavClick(item));
            navLinks.getChildren().add(link);
        }

        startProjectBtn.setOnAction(e -> SceneManager.getInstance().navigateToContact());
        if (viewOpeningsBtn != null) {
            viewOpeningsBtn.setOnAction(e -> SceneManager.getInstance().navigateToContact()); // Redirect to contact for
                                                                                              // now
        }
    }

    private void handleNavClick(String item) {
        switch (item) {
            case "Home" -> SceneManager.getInstance().navigateToHome();
            case "Services" -> SceneManager.getInstance().navigateToServices();
            case "Portfolio" -> SceneManager.getInstance().navigateToPortfolio();
            case "Our Engineers" -> SceneManager.getInstance().navigateToEngineers();
            case "Contribute" -> SceneManager.getInstance().navigateToContribute();
            case "Contact" -> SceneManager.getInstance().navigateToContact();
        }
    }
}
