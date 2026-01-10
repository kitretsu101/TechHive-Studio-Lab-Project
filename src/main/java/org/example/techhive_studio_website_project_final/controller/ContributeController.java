package org.example.techhive_studio_website_project_final.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import org.example.techhive_studio_website_project_final.data.ContributorRepository;
import org.example.techhive_studio_website_project_final.model.ContributorApplication;
import org.example.techhive_studio_website_project_final.service.FirestoreService;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

/**
 * Controller for the Contribute view.
 * Handles contributor application submissions with SQLite storage and Firestore
 * sync.
 */
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
    private Button submitButton;
    @FXML
    private Label messageLabel;

    private final ContributorRepository contributorRepository = ContributorRepository.getInstance();
    private final FirestoreService firestoreService = FirestoreService.getInstance();

    @FXML
    public void initialize() {
        expertiseCombo.setItems(FXCollections.observableArrayList(
                "Full-Stack Developer",
                "Frontend Specialist",
                "Backend Engineer",
                "UI/UX Designer",
                "DevOps Engineer",
                "Database Architect"));

        hideMessage();
    }

    @FXML
    private void onSubmitApplication() {
        // Validation
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String expertise = expertiseCombo.getValue();
        String portfolio = portfolioField.getText().trim();
        String about = aboutArea.getText().trim();

        if (name.isEmpty()) {
            showError("Please enter your full name.");
            return;
        }

        if (email.isEmpty() || !isValidEmail(email)) {
            showError("Please enter a valid email address.");
            return;
        }

        if (expertise == null) {
            showError("Please select your area of expertise.");
            return;
        }

        if (about.isEmpty()) {
            showError("Please tell us about yourself.");
            return;
        }

        // Show loading state
        setLoading(true);
        hideMessage();

        // Create application
        ContributorApplication application = new ContributorApplication(name, email, expertise, portfolio, about);

        // Save to SQLite and sync to Firestore
        CompletableFuture.runAsync(() -> {
            try {
                // Save locally first
                int id = contributorRepository.save(application);

                if (id > 0) {
                    // Try to sync to Firestore
                    firestoreService.saveContributorApplication(application)
                            .thenAccept(synced -> {
                                if (synced) {
                                    try {
                                        contributorRepository.markAsSynced(id);
                                    } catch (SQLException e) {
                                        System.err.println("Failed to mark as synced: " + e.getMessage());
                                    }
                                }
                            });

                    Platform.runLater(() -> {
                        setLoading(false);
                        showSuccess("Application submitted successfully! We'll review it and get back to you soon.");
                        clearForm();
                    });
                } else {
                    Platform.runLater(() -> {
                        setLoading(false);
                        showError("Failed to submit application. Please try again.");
                    });
                }
            } catch (SQLException e) {
                Platform.runLater(() -> {
                    setLoading(false);
                    showError("Error saving application: " + e.getMessage());
                });
            }
        });
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private void setLoading(boolean loading) {
        if (submitButton != null) {
            submitButton.setDisable(loading);
            submitButton.setText(loading ? "Submitting..." : "Submit Application");
        }
        if (nameField != null)
            nameField.setDisable(loading);
        if (emailField != null)
            emailField.setDisable(loading);
        if (expertiseCombo != null)
            expertiseCombo.setDisable(loading);
        if (portfolioField != null)
            portfolioField.setDisable(loading);
        if (aboutArea != null)
            aboutArea.setDisable(loading);
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        expertiseCombo.getSelectionModel().clearSelection();
        portfolioField.clear();
        aboutArea.clear();
    }

    private void showError(String message) {
        if (messageLabel != null) {
            messageLabel.setText(message);
            messageLabel.setStyle("-fx-text-fill: #ef4444;");
            messageLabel.setVisible(true);
            messageLabel.setManaged(true);
        }
    }

    private void showSuccess(String message) {
        if (messageLabel != null) {
            messageLabel.setText(message);
            messageLabel.setStyle("-fx-text-fill: #22c55e;");
            messageLabel.setVisible(true);
            messageLabel.setManaged(true);
        }
    }

    private void hideMessage() {
        if (messageLabel != null) {
            messageLabel.setVisible(false);
            messageLabel.setManaged(false);
        }
    }
}
