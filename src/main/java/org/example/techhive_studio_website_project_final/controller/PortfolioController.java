package org.example.techhive_studio_website_project_final.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import org.example.techhive_studio_website_project_final.data.DataStore;
import org.example.techhive_studio_website_project_final.model.Project;

public class PortfolioController {

    @FXML
    private FlowPane projectsGrid;

    @FXML
    public void initialize() {
        loadProjects();
    }

    private void loadProjects() {
        projectsGrid.getChildren().clear();
        for (Project project : DataStore.getInstance().getProjects()) {
            projectsGrid.getChildren().add(createProjectCard(project));
        }
    }

    private VBox createProjectCard(Project project) {
        VBox card = new VBox(15);
        card.getStyleClass().add("card");
        card.setPrefWidth(350);
        card.setPrefHeight(300);

        // Image Placeholder
        VBox imagePlaceholder = new VBox();
        imagePlaceholder.setStyle("-fx-background-color: #222; -fx-pref-height: 150; -fx-background-radius: 8;");

        Label title = new Label(project.getTitle());
        title.getStyleClass().add("h3");
        title.setStyle("-fx-font-size: 20px;");

        Label desc = new Label(project.getDescription());
        desc.getStyleClass().add("body-text");
        desc.setWrapText(true);

        FlowPane tags = new FlowPane();
        tags.setHgap(5);
        tags.setVgap(5);
        for (String tech : project.getTechStack()) {
            Label tag = new Label(tech);
            tag.setStyle(
                    "-fx-background-color: rgba(112, 0, 255, 0.2); -fx-text-fill: -color-secondary; -fx-padding: 2 8; -fx-background-radius: 4; -fx-font-size: 10px;");
            tags.getChildren().add(tag);
        }

        card.getChildren().addAll(imagePlaceholder, title, desc, tags);
        return card;
    }
}
