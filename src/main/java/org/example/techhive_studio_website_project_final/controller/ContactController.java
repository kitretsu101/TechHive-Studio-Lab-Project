package org.example.techhive_studio_website_project_final.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import org.example.techhive_studio_website_project_final.data.ContactRepository;
import org.example.techhive_studio_website_project_final.model.ContactInquiry;
import org.example.techhive_studio_website_project_final.service.FirestoreService;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

/**
 * Controller for the Contact view.
 * Handles project inquiry submissions with SQLite storage and Firestore sync.
 */
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
    private Button submitButton;
    @FXML
    private Label messageLabel;

    private final ContactRepository contactRepository = ContactRepository.getInstance();
    private final FirestoreService firestoreService = FirestoreService.getInstance();

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

        hideMessage();
    }

    @FXML
    private void onSubmit() {
        // Validation
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String projectType = projectTypeCombo.getValue();
        String budget = budgetCombo.getValue();
        String details = projectDetailsArea.getText().trim();

        if (name.isEmpty()) {
            showError("Please enter your name.");
            return;
        }

        if (email.isEmpty() || !isValidEmail(email)) {
            showError("Please enter a valid email address.");
            return;
        }

        if (projectType == null) {
            showError("Please select a project type.");
            return;
        }

        if (details.isEmpty()) {
            showError("Please provide project details.");
            return;
        }

        // Show loading state
        setLoading(true);
        hideMessage();

        // Create inquiry
        ContactInquiry inquiry = new ContactInquiry(name, email, projectType, budget, details);

        // Save to SQLite and sync to Firestore
        CompletableFuture.runAsync(() -> {
            try {
                // Save locally first
                int id = contactRepository.save(inquiry);

                if (id > 0) {
                    // Try to sync to Firestore
                    firestoreService.saveContactInquiry(inquiry)
                            .thenAccept(synced -> {
                                if (synced) {
                                    try {
                                        contactRepository.markAsSynced(id);
                                    } catch (SQLException e) {
                                        System.err.println("Failed to mark as synced: " + e.getMessage());
                                    }
                                }
                            });

                    Platform.runLater(() -> {
                        setLoading(false);
                        showSuccess(
                                "Thank you! Your inquiry has been submitted. We'll get back to you within 24 hours.");
                        clearForm();
                    });
                } else {
                    Platform.runLater(() -> {
                        setLoading(false);
                        showError("Failed to submit inquiry. Please try again.");
                    });
                }
            } catch (SQLException e) {
                Platform.runLater(() -> {
                    setLoading(false);
                    showError("Error saving inquiry: " + e.getMessage());
                });
            }
        });
    }

    @FXML
    private void onEmailUs() {
        try {
            java.awt.Desktop.getDesktop().mail(java.net.URI.create("mailto:dhruboplabon987@gmail.com"));
        } catch (Exception e) {
            showInfo("Email: dhruboplabon987@gmail.com");
        }
    }

    @FXML
    private void onCallUs() {
        showInfo("Phone: +8801871541511");
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private void setLoading(boolean loading) {
        if (submitButton != null) {
            submitButton.setDisable(loading);
            submitButton.setText(loading ? "Submitting..." : "Send Inquiry");
        }
        if (nameField != null)
            nameField.setDisable(loading);
        if (emailField != null)
            emailField.setDisable(loading);
        if (projectTypeCombo != null)
            projectTypeCombo.setDisable(loading);
        if (budgetCombo != null)
            budgetCombo.setDisable(loading);
        if (projectDetailsArea != null)
            projectDetailsArea.setDisable(loading);
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        projectTypeCombo.getSelectionModel().clearSelection();
        budgetCombo.getSelectionModel().clearSelection();
        projectDetailsArea.clear();
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

    private void showInfo(String message) {
        if (messageLabel != null) {
            messageLabel.setText(message);
            messageLabel.setStyle("-fx-text-fill: #3b82f6;");
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
