package com.techhive.model;

import java.util.List;

/**
 * Project model class representing a portfolio project.
 * Ready for future SQLite integration.
 */
public class Project {
    private int id;
    private String name;
    private String problem;
    private String solution;
    private String outcome;
    private List<String> techStack;
    private String imagePath;
    private String category;

    public Project(int id, String name, String problem, String solution, String outcome,
            List<String> techStack, String imagePath, String category) {
        this.id = id;
        this.name = name;
        this.problem = problem;
        this.solution = solution;
        this.outcome = outcome;
        this.techStack = techStack;
        this.imagePath = imagePath;
        this.category = category;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public String getImagePath() {
        return imagePath;
    }

    public String getCategory() {
        return category;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public void setTechStack(List<String> techStack) {
        this.techStack = techStack;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
