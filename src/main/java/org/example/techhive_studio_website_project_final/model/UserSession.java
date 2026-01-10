package org.example.techhive_studio_website_project_final.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Manages the current user session with persistence for "Remember Me"
 * functionality.
 */
public class UserSession {
    private static UserSession instance;
    private User currentUser;
    private boolean rememberMe;

    private static final String SESSION_FILE = "user_session.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     * Set the current user and optionally persist the session.
     */
    public void setCurrentUser(User user, boolean rememberMe) {
        this.currentUser = user;
        this.rememberMe = rememberMe;

        if (rememberMe && user != null) {
            saveSession();
        } else {
            clearSession();
        }
    }

    /**
     * Get the current logged-in user.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Check if a user is currently logged in.
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Save session to local file for persistence.
     */
    private void saveSession() {
        if (currentUser == null)
            return;

        try {
            File sessionFile = getSessionFile();
            try (FileWriter writer = new FileWriter(sessionFile)) {
                SessionData data = new SessionData(currentUser, rememberMe);
                gson.toJson(data, writer);
            }
            System.out.println("Session saved to: " + sessionFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to save session: " + e.getMessage());
        }
    }

    /**
     * Load a previously saved session on app startup.
     * 
     * @return true if a valid session was loaded
     */
    public boolean loadSession() {
        File sessionFile = getSessionFile();
        if (!sessionFile.exists()) {
            return false;
        }

        try (FileReader reader = new FileReader(sessionFile)) {
            SessionData data = gson.fromJson(reader, SessionData.class);
            if (data != null && data.user != null && data.rememberMe) {
                this.currentUser = data.user;
                this.rememberMe = true;
                System.out.println("Session loaded for user: " + currentUser.getEmail());
                return true;
            }
        } catch (IOException e) {
            System.err.println("Failed to load session: " + e.getMessage());
        }
        return false;
    }

    /**
     * Clear the saved session (logout).
     */
    public void clearSession() {
        this.currentUser = null;
        this.rememberMe = false;

        File sessionFile = getSessionFile();
        if (sessionFile.exists()) {
            sessionFile.delete();
        }
    }

    /**
     * Logout the current user.
     */
    public void logout() {
        clearSession();
    }

    private File getSessionFile() {
        String userHome = System.getProperty("user.home");
        File appDir = new File(userHome, ".techhive");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        return new File(appDir, SESSION_FILE);
    }

    /**
     * Inner class for session data serialization.
     */
    private static class SessionData {
        User user;
        boolean rememberMe;

        SessionData(User user, boolean rememberMe) {
            this.user = user;
            this.rememberMe = rememberMe;
        }
    }
}
