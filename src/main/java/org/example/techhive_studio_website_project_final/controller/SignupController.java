package org.example.techhive_studio_website_project_final.controller;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.example.techhive_studio_website_project_final.core.SceneManager;
import org.example.techhive_studio_website_project_final.service.FirebaseAuthService;

/**
 * Controller for the Signup view.
 * Handles user registration with Firebase Authentication.
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
    private Button signupButton;

    private final FirebaseAuthService authService = FirebaseAuthService.getInstance();

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

        hideMessage();
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

        // Show loading state
        setLoading(true);
        hideMessage();

        // Firebase registration
        authService.signUpWithEmail(email, password, fullName, role)
                .thenAccept(result -> Platform.runLater(() -> {
                    setLoading(false);

                    if (result.isSuccess()) {
                        showSuccess("Account created successfully! Redirecting to login...");

                        System.out.println("User registered: " + result.getUser());

                        // Navigate to login after short delay
                        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                        pause.setOnFinished(e -> SceneManager.getInstance().switchViewWithFade("fxml/LoginView.fxml"));
                        pause.play();
                    } else {
                        showError(result.getErrorMessage());
                    }
                }))
                .exceptionally(ex -> {
                    Platform.runLater(() -> {
                        setLoading(false);
                        showError("Registration failed: " + ex.getMessage());
                    });
                    return null;
                });
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

    private void setLoading(boolean loading) {
        if (signupButton != null) {
            signupButton.setDisable(loading);
            signupButton.setText(loading ? "Creating Account..." : "Create Account →");
        }
        if (fullNameField != null)
            fullNameField.setDisable(loading);
        if (emailField != null)
            emailField.setDisable(loading);
        if (passwordField != null)
            passwordField.setDisable(loading);
        if (confirmPasswordField != null)
            confirmPasswordField.setDisable(loading);
        if (roleComboBox != null)
            roleComboBox.setDisable(loading);
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
