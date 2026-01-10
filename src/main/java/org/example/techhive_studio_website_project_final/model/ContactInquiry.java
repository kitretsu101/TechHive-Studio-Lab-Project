package org.example.techhive_studio_website_project_final.model;

import java.time.LocalDateTime;

/**
 * Model class representing a contact inquiry submission.
 */
public class ContactInquiry {
    private int id;
    private String name;
    private String email;
    private String projectType;
    private String budget;
    private String details;
    private LocalDateTime submittedAt;
    private boolean syncedToCloud;

    public ContactInquiry() {
        this.submittedAt = LocalDateTime.now();
        this.syncedToCloud = false;
    }

    public ContactInquiry(String name, String email, String projectType, String budget, String details) {
        this();
        this.name = name;
        this.email = email;
        this.projectType = projectType;
        this.budget = budget;
        this.details = details;
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

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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
        return "ContactInquiry{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", projectType='" + projectType + '\'' +
                ", budget='" + budget + '\'' +
                ", submittedAt=" + submittedAt +
                '}';
    }
}
