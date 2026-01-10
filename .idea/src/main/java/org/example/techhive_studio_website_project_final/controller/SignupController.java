package org.example.techhive_studio_website_project_final.controller;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.example.techhive_studio_website_project_final.core.SceneManager;

/**
 * Controller for the Signup view.
 * Handles user registration with validation.
 * Ready for future SQLite integration.
 */
public class SignupController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Label messageLabel;

    @FXML
    private HBox rootContainer;

    @FXML
    public void initialize() {
        // Populate role options
        roleComboBox.setItems(FXCollections.observableArrayList(
                "Client",
                "Engineer",
                "Admin"));

        // Add fade-in animation when view loads
        if (rootContainer != null) {
            rootContainer.setOpacity(0);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), rootContainer);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        }
    }

    @FXML
    private void onSignupClick() {
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String role = roleComboBox.getValue();

        // Validation
        if (fullName.isEmpty()) {
            showError("Please enter your full name.");
            return;
        }

        if (email.isEmpty()) {
            showError("Please enter your email address.");
            return;
        }

        if (!isValidEmail(email)) {
            showError("Please enter a valid email address.");
            return;
        }

        if (password.isEmpty()) {
            showError("Please create a password.");
            return;
        }

        if (password.length() < 6) {
            showError("Password must be at least 6 characters long.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match.");
            return;
        }

        // Dummy registration logic
        // TODO: Replace with SQLite database insert
        if (registerUser(fullName, email, password, role)) {
            showSuccess("Account created successfully! Redirecting to login...");

            // Navigate to login after short delay
            javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> SceneManager.getInstance().switchViewWithFade("fxml/LoginView.fxml"));
            pause.play();
        } else {
            showError("Registration failed. Please try again.");
        }
    }

    /**
     * Dummy registration method.
     * Replace this with actual database insert later.
     */
    private boolean registerUser(String fullName, String email, String password, String role) {
        // For now, always return true (dummy success)
        // TODO: Insert user into SQLite database
        System.out.println("User Registered:");
        System.out.println("  Name: " + fullName);
        System.out.println("  Email: " + email);
        System.out.println("  Role: " + (role != null ? role : "Not specified"));
        return true;
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    @FXML
    private void onLoginClick() {
        SceneManager.getInstance().switchViewWithFade("fxml/LoginView.fxml");
    }

    @FXML
    private void onBackToHomeClick() {
        SceneManager.getInstance().switchViewWithFade("fxml/HomeView.fxml");
    }

    private void showError(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: #ef4444;");
        messageLabel.setVisible(true);
        messageLabel.setManaged(true);
    }

    private void showSuccess(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: #22c55e;");
        messageLabel.setVisible(true);
        messageLabel.setManaged(true);
    }
}
