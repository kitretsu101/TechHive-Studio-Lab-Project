package org.example.techhive_studio_website_project_final.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.example.techhive_studio_website_project_final.core.SceneManager;

/**
 * Controller for the Login view.
 * Handles user authentication with dummy logic for now.
 * Ready for future SQLite integration.
 */
public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberMeCheckbox;

    @FXML
    private Label errorLabel;

    @FXML
    private HBox rootContainer;

    // Dummy credentials for testing
    private static final String VALID_EMAIL = "admin@techhive.com";
    private static final String VALID_PASSWORD = "password123";

    @FXML
    public void initialize() {
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
    private void onLoginClick() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        // Validate inputs
        if (email.isEmpty() || password.isEmpty()) {
            showError("Please enter both email and password.");
            return;
        }

        // Dummy authentication logic
        // TODO: Replace with SQLite database lookup
        if (authenticateUser(email, password)) {
            hideError();
            // Navigate to home on successful login
            SceneManager.getInstance().switchViewWithFade("fxml/HomeView.fxml");
        } else {
            showError("Invalid email or password. Try admin@techhive.com / password123");
        }
    }

    /**
     * Dummy authentication method.
     * Replace this with actual database authentication later.
     */
    private boolean authenticateUser(String email, String password) {
        return VALID_EMAIL.equalsIgnoreCase(email) && VALID_PASSWORD.equals(password);
    }

    @FXML
    private void onForgotPasswordClick() {
        // TODO: Implement forgot password flow
        showError("Password reset functionality coming soon!");
    }

    @FXML
    private void onSignupClick() {
        SceneManager.getInstance().switchViewWithFade("fxml/SignupView.fxml");
    }

    @FXML
    private void onBackToHomeClick() {
        SceneManager.getInstance().switchViewWithFade("fxml/HomeView.fxml");
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    private void hideError() {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }
}
