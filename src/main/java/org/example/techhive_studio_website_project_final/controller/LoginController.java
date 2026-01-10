package org.example.techhive_studio_website_project_final.controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.example.techhive_studio_website_project_final.core.SceneManager;
import org.example.techhive_studio_website_project_final.model.User;
import org.example.techhive_studio_website_project_final.model.UserSession;
import org.example.techhive_studio_website_project_final.service.AdminSession;
import org.example.techhive_studio_website_project_final.service.FirebaseAuthService;

/**
 * Controller for the Login view.
 * Handles user authentication with Firebase.
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

    @FXML
    private Button loginButton;

    @FXML
    private Button googleButton;

    @FXML
    private Button linkedInButton;

    private final FirebaseAuthService authService = FirebaseAuthService.getInstance();

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

        hideError();
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

        if (!isValidEmail(email)) {
            showError("Please enter a valid email address.");
            return;
        }

        // Show loading state
        setLoading(true);
        hideError();

        // Check for admin credentials (hardcoded for now)
        if (AdminSession.getInstance().isAdminCredentials(email, password)) {
            setLoading(false);
            User adminUser = new User("admin-001", email, "Admin", "Admin", "email");
            AdminSession.getInstance().setLoggedIn(adminUser, true);
            UserSession.getInstance().setCurrentUser(adminUser,
                    rememberMeCheckbox != null && rememberMeCheckbox.isSelected());

            System.out.println("[ADMIN] Admin login successful: " + email);
            SceneManager.getInstance().switchViewWithFade("fxml/AdminDatabaseView.fxml");
            return;
        }

        // Firebase authentication for regular users
        authService.signInWithEmail(email, password)
                .thenAccept(result -> Platform.runLater(() -> {
                    setLoading(false);

                    if (result.isSuccess()) {
                        User user = result.getUser();
                        boolean remember = rememberMeCheckbox != null && rememberMeCheckbox.isSelected();

                        // Save session (not admin)
                        UserSession.getInstance().setCurrentUser(user, remember);
                        AdminSession.getInstance().setLoggedIn(user, false);

                        System.out.println("Login successful: " + user.getEmail());

                        // Navigate to home
                        SceneManager.getInstance().switchViewWithFade("fxml/HomeView.fxml");
                    } else {
                        showError(result.getErrorMessage());
                    }
                }))
                .exceptionally(ex -> {
                    Platform.runLater(() -> {
                        setLoading(false);
                        showError("Login failed: " + ex.getMessage());
                    });
                    return null;
                });
    }

    @FXML
    private void onGoogleSignInClick() {
        showInfo("Opening Google Sign-In...");

        authService.signInWithGoogle(new FirebaseAuthService.AuthCallback() {
            @Override
            public void onSuccess(User user) {
                Platform.runLater(() -> {
                    UserSession.getInstance().setCurrentUser(user, true);
                    SceneManager.getInstance().switchViewWithFade("fxml/HomeView.fxml");
                });
            }

            @Override
            public void onError(String message) {
                Platform.runLater(() -> showError(message));
            }

            @Override
            public void onInfo(String message) {
                Platform.runLater(() -> showInfo(message));
            }
        });
    }

    @FXML
    private void onLinkedInSignInClick() {
        showInfo("Opening LinkedIn Sign-In...");

        authService.signInWithLinkedIn(new FirebaseAuthService.AuthCallback() {
            @Override
            public void onSuccess(User user) {
                Platform.runLater(() -> {
                    UserSession.getInstance().setCurrentUser(user, true);
                    SceneManager.getInstance().switchViewWithFade("fxml/HomeView.fxml");
                });
            }

            @Override
            public void onError(String message) {
                Platform.runLater(() -> showError(message));
            }

            @Override
            public void onInfo(String message) {
                Platform.runLater(() -> showInfo(message));
            }
        });
    }

    @FXML
    private void onForgotPasswordClick() {
        showInfo("Password reset functionality coming soon!");
    }

    @FXML
    private void onSignupClick() {
        SceneManager.getInstance().switchViewWithFade("fxml/SignupView.fxml");
    }

    @FXML
    private void onBackToHomeClick() {
        SceneManager.getInstance().switchViewWithFade("fxml/HomeView.fxml");
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private void setLoading(boolean loading) {
        if (loginButton != null) {
            loginButton.setDisable(loading);
            loginButton.setText(loading ? "Signing in..." : "Sign In →");
        }
        if (emailField != null)
            emailField.setDisable(loading);
        if (passwordField != null)
            passwordField.setDisable(loading);
        if (googleButton != null)
            googleButton.setDisable(loading);
        if (linkedInButton != null)
            linkedInButton.setDisable(loading);
    }

    private void showError(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
            errorLabel.setStyle("-fx-text-fill: #ef4444;");
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
        }
    }

    private void showInfo(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
            errorLabel.setStyle("-fx-text-fill: #3b82f6;");
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
        }
    }

    private void hideError() {
        if (errorLabel != null) {
            errorLabel.setVisible(false);
            errorLabel.setManaged(false);
        }
    }
}
