package org.example.techhive_studio_website_project_final.model;

import java.time.LocalDateTime;

/**
 * Model class representing a contributor application submission.
 */
public class ContributorApplication {
    private int id;
    private String name;
    private String email;
    private String expertise;
    private String portfolioUrl;
    private String about;
    private String status; // "pending", "approved", "rejected"
    private LocalDateTime submittedAt;
    private boolean syncedToCloud;

    public ContributorApplication() {
        this.submittedAt = LocalDateTime.now();
        this.status = "pending";
        this.syncedToCloud = false;
    }

    public ContributorApplication(String name, String email, String expertise, String portfolioUrl, String about) {
        this();
        this.name = name;
        this.email = email;
        this.expertise = expertise;
        this.portfolioUrl = portfolioUrl;
        this.about = about;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getPortfolioUrl() {
        return portfolioUrl;
    }

    public void setPortfolioUrl(String portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public boolean isSyncedToCloud() {
        return syncedToCloud;
    }

    public void setSyncedToCloud(boolean syncedToCloud) {
        this.syncedToCloud = syncedToCloud;
    }

    @Override
    public String toString() {
        return "ContributorApplication{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", expertise='" + expertise + '\'' +
                ", status='" + status + '\'' +
                ", submittedAt=" + submittedAt +
                '}';
    }
}
