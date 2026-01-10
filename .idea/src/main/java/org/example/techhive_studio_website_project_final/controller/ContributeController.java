package org.example.techhive_studio_website_project_final.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ContributeController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private ComboBox<String> expertiseCombo;
    @FXML
    private TextField portfolioField;
    @FXML
    private TextArea aboutArea;

    @FXML
    public void initialize() {
        expertiseCombo.setItems(FXCollections.observableArrayList(
                "Full-Stack Developer",
                "Frontend Specialist",
                "Backend Engineer",
                "UI/UX Designer",
                "DevOps Engineer",
                "Database Architect"));
    }

    @FXML
    private void onSubmitApplication() {
        System.out.println("Application Received: " + nameField.getText());
        // Add submission logic here
        clearForm();
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        expertiseCombo.getSelectionModel().clearSelection();
        portfolioField.clear();
        aboutArea.clear();
    }
}
