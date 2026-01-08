package com.techhive.controller;

import com.techhive.util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ContactController implements Initializable {

    @FXML
    private Label logoLabel;
    @FXML
    private HBox navLinks;
    @FXML
    private Button startProjectBtn;

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField subjectField;
    @FXML
    private TextArea messageArea;
    @FXML
    private Button submitBtn;
    @FXML
    private Label statusLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupNavigation();
        setupForm();
    }

    private void setupNavigation() {
        logoLabel.setOnMouseClicked(e -> SceneManager.getInstance().navigateToHome());

        String[] navItems = { "Home", "Services", "Portfolio", "Our Engineers", "Contribute", "Contact" };
        navLinks.getChildren().clear();

        for (String item : navItems) {
            Label link = new Label(item);
            link.getStyleClass().add("nav-link");
            if (item.equals("Contact")) {
                link.getStyleClass().add("nav-link-active");
            }
            link.setOnMouseClicked(e -> handleNavClick(item));
            navLinks.getChildren().add(link);
        }

        // Already on contact page
        startProjectBtn.setDisable(true);
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

    private void setupForm() {
        submitBtn.setOnAction(e -> handleSubmit());
    }

    private void handleSubmit() {
        // Logic to handle form submission (e.g., store in DB later)
        String name = nameField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || email.isEmpty()) {
            statusLabel.setText("Please fill in required fields.");
            statusLabel.setStyle("-fx-text-fill: #ec4899;"); // Pink error
        } else {
            statusLabel.setText("Message sent successfully! We'll get back to you soon.");
            statusLabel.setStyle("-fx-text-fill: #00d4ff;"); // Blue success
            clearForm();
        }
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        subjectField.clear();
        messageArea.clear();
    }
}
