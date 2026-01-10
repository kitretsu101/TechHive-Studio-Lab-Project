package org.example.techhive_studio_website_project_final.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.techhive_studio_website_project_final.model.User;
import org.example.techhive_studio_website_project_final.model.UserSession;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

/**
 * Service for Firebase Authentication operations.
 * Uses Firebase REST API for authentication (suitable for desktop apps).
 */
public class FirebaseAuthService {
    private static FirebaseAuthService instance;
    private String apiKey;
    private final Gson gson = new Gson();

    // Firebase Auth REST API endpoints
    private static final String SIGNUP_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=";
    private static final String SIGNIN_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=";
    private static final String GOOGLE_SIGNIN_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithIdp?key=";

    private FirebaseAuthService() {
    }

    public static FirebaseAuthService getInstance() {
        if (instance == null) {
            instance = new FirebaseAuthService();
        }
        return instance;
    }

    /**
     * Initialize Firebase with API key from config file.
     */
    public void initialize() {
        try {
            InputStream is = getClass().getResourceAsStream("/firebase-config.json");
            if (is != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    JsonObject config = gson.fromJson(reader, JsonObject.class);
                    this.apiKey = config.get("apiKey").getAsString();
                    System.out.println("Firebase Auth initialized with project: " +
                            config.get("projectId").getAsString());
                }
            } else {
                System.err.println("firebase-config.json not found in resources!");
                // Use placeholder for development
                this.apiKey = "YOUR_API_KEY";
            }
        } catch (Exception e) {
            System.err.println("Failed to initialize Firebase: " + e.getMessage());
        }
    }

    /**
     * Sign up a new user with email and password.
     */
    public CompletableFuture<AuthResult> signUpWithEmail(String email, String password, String displayName,
            String role) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL url = URI.create(SIGNUP_URL + apiKey).toURL();
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                JsonObject payload = new JsonObject();
                payload.addProperty("email", email);
                payload.addProperty("password", password);
                payload.addProperty("returnSecureToken", true);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(payload.toString().getBytes(StandardCharsets.UTF_8));
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    JsonObject response = readResponse(conn);
                    String uid = response.get("localId").getAsString();
                    String idToken = response.get("idToken").getAsString();

                    User user = new User(uid, email, displayName, role, "email");
                    return new AuthResult(true, user, idToken, null);
                } else {
                    JsonObject error = readErrorResponse(conn);
                    String errorMessage = parseFirebaseError(error);
                    return new AuthResult(false, null, null, errorMessage);
                }
            } catch (Exception e) {
                return new AuthResult(false, null, null, "Network error: " + e.getMessage());
            }
        });
    }

    /**
     * Sign in with email and password.
     */
    public CompletableFuture<AuthResult> signInWithEmail(String email, String password) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL url = URI.create(SIGNIN_URL + apiKey).toURL();
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                JsonObject payload = new JsonObject();
                payload.addProperty("email", email);
                payload.addProperty("password", password);
                payload.addProperty("returnSecureToken", true);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(payload.toString().getBytes(StandardCharsets.UTF_8));
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    JsonObject response = readResponse(conn);
                    String uid = response.get("localId").getAsString();
                    String userEmail = response.get("email").getAsString();
                    String idToken = response.get("idToken").getAsString();
                    String displayName = response.has("displayName") ? response.get("displayName").getAsString()
                            : userEmail.split("@")[0];

                    User user = new User(uid, userEmail, displayName, null, "email");
                    return new AuthResult(true, user, idToken, null);
                } else {
                    JsonObject error = readErrorResponse(conn);
                    String errorMessage = parseFirebaseError(error);
                    return new AuthResult(false, null, null, errorMessage);
                }
            } catch (Exception e) {
                return new AuthResult(false, null, null, "Network error: " + e.getMessage());
            }
        });
    }

    /**
     * Initiate Google Sign-In flow.
     * Opens the system browser for OAuth.
     */
    public void signInWithGoogle(AuthCallback callback) {
        // For desktop apps, we use a custom OAuth flow
        // This opens a browser window for Google authentication
        try {
            String clientId = getGoogleClientId();
            String redirectUri = "urn:ietf:wg:oauth:2.0:oob"; // Desktop app redirect

            String authUrl = "https://accounts.google.com/o/oauth2/v2/auth?" +
                    "client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8) +
                    "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8) +
                    "&response_type=code" +
                    "&scope=" + URLEncoder.encode("email profile", StandardCharsets.UTF_8);

            // Open in default browser
            java.awt.Desktop.getDesktop().browse(URI.create(authUrl));

            // Note: For full implementation, you'd need to:
            // 1. Start a local server to receive the callback
            // 2. Exchange the authorization code for tokens
            // For now, we'll show a dialog asking the user to paste the code
            callback.onInfo("Google Sign-In opened in browser. Please complete authentication.");

        } catch (Exception e) {
            callback.onError("Failed to open Google Sign-In: " + e.getMessage());
        }
    }

    /**
     * Initiate LinkedIn Sign-In flow.
     */
    public void signInWithLinkedIn(AuthCallback callback) {
        try {
            String clientId = getLinkedInClientId();
            String redirectUri = "http://localhost:8080/callback"; // Local callback

            String authUrl = "https://www.linkedin.com/oauth/v2/authorization?" +
                    "response_type=code" +
                    "&client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8) +
                    "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8) +
                    "&scope=" + URLEncoder.encode("r_liteprofile r_emailaddress", StandardCharsets.UTF_8);

            java.awt.Desktop.getDesktop().browse(URI.create(authUrl));
            callback.onInfo("LinkedIn Sign-In opened in browser. Please complete authentication.");

        } catch (Exception e) {
            callback.onError("Failed to open LinkedIn Sign-In: " + e.getMessage());
        }
    }

    /**
     * Sign out the current user.
     */
    public void signOut() {
        UserSession.getInstance().logout();
        System.out.println("User signed out.");
    }

    /**
     * Get the currently logged-in user.
     */
    public User getCurrentUser() {
        return UserSession.getInstance().getCurrentUser();
    }

    /**
     * Check if a user is currently logged in.
     */
    public boolean isLoggedIn() {
        return UserSession.getInstance().isLoggedIn();
    }

    // Helper methods
    private JsonObject readResponse(HttpURLConnection conn) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            return gson.fromJson(reader, JsonObject.class);
        }
    }

    private JsonObject readErrorResponse(HttpURLConnection conn) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
            return gson.fromJson(reader, JsonObject.class);
        }
    }

    private String parseFirebaseError(JsonObject error) {
        try {
            String code = error.getAsJsonObject("error")
                    .get("message").getAsString();

            return switch (code) {
                case "EMAIL_EXISTS" -> "This email is already registered.";
                case "INVALID_EMAIL" -> "Please enter a valid email address.";
                case "WEAK_PASSWORD" -> "Password should be at least 6 characters.";
                case "EMAIL_NOT_FOUND" -> "No account found with this email.";
                case "INVALID_PASSWORD" -> "Incorrect password.";
                case "USER_DISABLED" -> "This account has been disabled.";
                case "INVALID_LOGIN_CREDENTIALS" -> "Invalid email or password.";
                default -> "Authentication failed: " + code;
            };
        } catch (Exception e) {
            return "Authentication failed. Please try again.";
        }
    }

    private String getGoogleClientId() {
        // In production, this should come from a secure config
        return "YOUR_GOOGLE_CLIENT_ID.apps.googleusercontent.com";
    }

    private String getLinkedInClientId() {
        // In production, this should come from a secure config
        return "YOUR_LINKEDIN_CLIENT_ID";
    }

    /**
     * Result class for authentication operations.
     */
    public static class AuthResult {
        private final boolean success;
        private final User user;
        private final String idToken;
        private final String errorMessage;

        public AuthResult(boolean success, User user, String idToken, String errorMessage) {
            this.success = success;
            this.user = user;
            this.idToken = idToken;
            this.errorMessage = errorMessage;
        }

        public boolean isSuccess() {
            return success;
        }

        public User getUser() {
            return user;
        }

        public String getIdToken() {
            return idToken;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    /**
     * Callback interface for async auth operations.
     */
    public interface AuthCallback {
        void onSuccess(User user);

        void onError(String message);

        void onInfo(String message);
    }
}
