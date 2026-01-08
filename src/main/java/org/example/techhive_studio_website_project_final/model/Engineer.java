package org.example.techhive_studio_website_project_final.model;

import java.util.List;

public class Engineer {
    private String id;
    private String name;
    private String role;
    private String bio;
    private String imageUrl;
    private List<String> skills;

    public Engineer(String id, String name, String role, String bio, String imageUrl, List<String> skills) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.bio = bio;
        this.imageUrl = imageUrl;
        this.skills = skills;
    }

    public String getId() {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getSkills() {
        return skills;
    }
}
