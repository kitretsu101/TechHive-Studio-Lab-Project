package org.example.techhive_studio_website_project_final.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ContactController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private ComboBox<String> projectTypeCombo;
    @FXML
    private ComboBox<String> budgetCombo;
    @FXML
    private TextArea projectDetailsArea;

    @FXML
    public void initialize() {
        projectTypeCombo.setItems(FXCollections.observableArrayList(
                "Web Development",
                "Mobile App",
                "UI/UX Design",
                "Cloud Infrastructure",
                "Consulting",
                "Other"));

        budgetCombo.setItems(FXCollections.observableArrayList(
                "<$1,000",
                "$1,000 - $5,000",
                "$5,000 - $10,000",
                "$10,000 - $50,000",
                "$50,000+"));
    }

    @FXML
    private void onSubmit() {
        System.out.println("Project Inquiry: " + nameField.getText());
        // Logic to process inquiry
        clearForm();
    }

    @FXML
    private void onEmailUs() {
        System.out.println("Email Us clicked");
    }

    @FXML
    private void onCallUs() {
        System.out.println("Call Us clicked");
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        projectTypeCombo.getSelectionModel().clearSelection();
        budgetCombo.getSelectionModel().clearSelection();
        projectDetailsArea.clear();
    }
}
