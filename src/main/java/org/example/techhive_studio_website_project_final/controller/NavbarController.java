package org.example.techhive_studio_website_project_final.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.techhive_studio_website_project_final.core.SceneManager;
import org.example.techhive_studio_website_project_final.service.AdminSession;

/**
 * Controller for the dynamic navigation bar.
 * Shows different menu items based on user role (admin vs regular user).
 */
public class NavbarController {

    @FXML
    private Button contributeButton;

    @FXML
    private Button contactButton;

    @FXML
    private Button databaseButton;

    @FXML
    private Button loginButton;

    @FXML
    public void initialize() {
        updateNavbarForRole();
    }

    /**
     * Update navbar visibility based on admin status.
     */
    public void updateNavbarForRole() {
        boolean isAdmin = AdminSession.getInstance().isAdmin();
        boolean isLoggedIn = AdminSession.getInstance().isLoggedIn();

        // Show/hide items based on role
        if (contributeButton != null) {
            contributeButton.setVisible(!isAdmin);
            contributeButton.setManaged(!isAdmin);
        }

        if (contactButton != null) {
            contactButton.setVisible(!isAdmin);
            contactButton.setManaged(!isAdmin);
        }

        if (databaseButton != null) {
            databaseButton.setVisible(isAdmin);
            databaseButton.setManaged(isAdmin);
        }

        // Update login button text
        if (loginButton != null) {
            if (isLoggedIn) {
                loginButton.setText(isAdmin ? "Admin Panel" : "Account");
            } else {
                loginButton.setText("Login");
            }
        }
    }

    @FXML
    private void onHomeClick() {
        SceneManager.getInstance().switchView("fxml/HomeView.fxml");
    }

    @FXML
    private void onServicesClick() {
        SceneManager.getInstance().switchView("fxml/ServicesView.fxml");
    }

    @FXML
    private void onPortfolioClick() {
        SceneManager.getInstance().switchView("fxml/PortfolioView.fxml");
    }

    @FXML
    private void onEngineersClick() {
        SceneManager.getInstance().switchViewWithFade("fxml/EngineersView.fxml");
    }

    @FXML
    private void onContributeClick() {
        SceneManager.getInstance().switchView("fxml/ContributeView.fxml");
    }

    @FXML
    private void onContactClick() {
        SceneManager.getInstance().switchView("fxml/ContactView.fxml");
    }

    @FXML
    private void onDatabaseClick() {
        if (AdminSession.getInstance().isAdmin()) {
            SceneManager.getInstance().switchViewWithFade("fxml/AdminDatabaseView.fxml");
        }
    }

    @FXML
    private void onStartProjectClick() {
        SceneManager.getInstance().switchView("fxml/ContactView.fxml");
    }

    @FXML
    private void onLoginClick() {
        if (AdminSession.getInstance().isLoggedIn()) {
            if (AdminSession.getInstance().isAdmin()) {
                SceneManager.getInstance().switchViewWithFade("fxml/AdminDatabaseView.fxml");
            } else {
                // Could open user profile page here
                SceneManager.getInstance().switchViewWithFade("fxml/HomeView.fxml");
            }
        } else {
            SceneManager.getInstance().switchViewWithFade("fxml/LoginView.fxml");
        }
    }

    @FXML
    private void onLogoutClick() {
        AdminSession.getInstance().logout();
        SceneManager.getInstance().switchViewWithFade("fxml/HomeView.fxml");
    }
}
