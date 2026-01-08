package org.example.techhive_studio_website_project_final.model;

import java.util.List;

public class Project {
    private String id;
    private String title;
    private String description;
    private String problem;
    private String solution;
    private String outcome;
    private List<String> techStack;
    private String imageUrl;

    public Project(String id, String title, String description, String problem, String solution, String outcome,
            List<String> techStack, String imageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.problem = problem;
        this.solution = solution;
        this.outcome = outcome;
        this.techStack = techStack;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getProblem() {
        return problem;
    }

    public String getSolution() {
        return solution;
    }

    public String getOutcome() {
        return outcome;
    }

    public List<String> getTechStack() {
        return techStack;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
