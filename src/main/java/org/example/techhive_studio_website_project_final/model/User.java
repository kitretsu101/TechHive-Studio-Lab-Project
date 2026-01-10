package org.example.techhive_studio_website_project_final.model;

/**
 * User model representing an authenticated user.
 */
public class User {
    private String uid;
    private String email;
    private String displayName;
    private String role;
    private String provider; // "email", "google", "linkedin"

    public User() {
    }

    public User(String uid, String email, String displayName, String role, String provider) {
        this.uid = uid;
        this.email = email;
        this.displayName = displayName;
        this.role = role;
        this.provider = provider;
    }

    // Getters and Setters
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", displayName='" + displayName + '\'' +
                ", role='" + role + '\'' +
                ", provider='" + provider + '\'' +
                '}';
    }
}
