package org.example.techhive_studio_website_project_final.service;

import org.example.techhive_studio_website_project_final.model.User;

/**
 * Manages admin session state and authentication.
 * Hardcoded credentials for development, ready for SQLite integration.
 */
public class AdminSession {
    private static AdminSession instance;

    // Hardcoded admin credentials (will be moved to SQLite later)
    private static final String ADMIN_EMAIL = "dhruboplabon987@gmail.com";
    private static final String ADMIN_PASSWORD = "123456789";

    private boolean isAdmin = false;
    private boolean isLoggedIn = false;
    private User currentUser;

    private AdminSession() {
    }

    public static AdminSession getInstance() {
        if (instance == null) {
            instance = new AdminSession();
        }
        return instance;
    }

    /**
     * Check if credentials match admin account.
     * 
     * @return true if admin credentials
     */
    public boolean isAdminCredentials(String email, String password) {
        return ADMIN_EMAIL.equalsIgnoreCase(email) && ADMIN_PASSWORD.equals(password);
    }

    /**
     * Set the login state and admin flag.
     */
    public void setLoggedIn(User user, boolean isAdmin) {
        this.currentUser = user;
        this.isLoggedIn = true;
        this.isAdmin = isAdmin;
    }

    /**
     * Logout and clear session.
     */
    public void logout() {
        this.currentUser = null;
        this.isLoggedIn = false;
        this.isAdmin = false;
    }

    /**
     * Check if current user is admin.
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Check if user is logged in.
     */
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Get current user.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Get admin email for reference.
     * TODO: Replace with database lookup
     */
    public static String getAdminEmail() {
        return ADMIN_EMAIL;
    }
}
