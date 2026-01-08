package com.techhive.model;

import java.util.List;

/**
 * Engineer model class representing a team member.
 * Ready for future SQLite integration.
 */
public class Engineer {
    private int id;
    private String name;
    private String role;
    private String bio;
    private String photoPath;
    private List<String> skills;

    public Engineer(int id, String name, String role, String bio, String photoPath, List<String> skills) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.bio = bio;
        this.photoPath = photoPath;
        this.skills = skills;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getBio() {
        return bio;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public List<String> getSkills() {
        return skills;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    /**
     * Get the category for filtering purposes.
     */
    public String getCategory() {
        if (role.toLowerCase().contains("frontend"))
            return "Frontend Engineer";
        if (role.toLowerCase().contains("backend"))
            return "Backend Engineer";
        if (role.toLowerCase().contains("fullstack") || role.toLowerCase().contains("full stack"))
            return "Fullstack Engineer";
        if (role.toLowerCase().contains("devops"))
            return "DevOps Engineer";
        if (role.toLowerCase().contains("design") || role.toLowerCase().contains("ui")
                || role.toLowerCase().contains("ux"))
            return "Product Designer";
        return "All";
    }
}
