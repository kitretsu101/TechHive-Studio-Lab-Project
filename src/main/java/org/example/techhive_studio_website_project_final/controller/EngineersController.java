package org.example.techhive_studio_website_project_final.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import org.example.techhive_studio_website_project_final.core.SceneManager;
import org.example.techhive_studio_website_project_final.data.DataStore;
import org.example.techhive_studio_website_project_final.model.Engineer;

import java.util.List;
import java.util.stream.Collectors;

public class EngineersController {

    @FXML
    private HBox filterButtonsBox;
    @FXML
    private VBox ceoSection;
    @FXML
    private HBox ceoCardContainer;
    @FXML
    private VBox foundersSection;
    @FXML
    private FlowPane foundersGrid;
    @FXML
    private VBox teamSection;
    @FXML
    private FlowPane teamGrid;

    @FXML
    private Button filterAll;
    @FXML
    private Button filterFrontend;
    @FXML
    private Button filterBackend;
    @FXML
    private Button filterFullstack;
    @FXML
    private Button filterDevOps;
    @FXML
    private Button filterDesigner;

    private String currentFilter = "All";

    @FXML
    public void initialize() {
        loadEngineers("All");
        updateFilterButtonStyles();
    }

    @FXML
    private void onFilterAll() {
        setFilter("All");
    }

    @FXML
    private void onFilterFrontend() {
        setFilter("Frontend");
    }

    @FXML
    private void onFilterBackend() {
        setFilter("Backend");
    }

    @FXML
    private void onFilterFullstack() {
        setFilter("Fullstack");
    }

    @FXML
    private void onFilterDevOps() {
        setFilter("DevOps");
    }

    @FXML
    private void onFilterDesigner() {
        setFilter("Designer");
    }

    private void setFilter(String filter) {
        currentFilter = filter;
        loadEngineers(filter);
        updateFilterButtonStyles();
    }

    private void updateFilterButtonStyles() {
        // Remove active class from all buttons
        filterAll.getStyleClass().remove("filter-button-active");
        filterFrontend.getStyleClass().remove("filter-button-active");
        filterBackend.getStyleClass().remove("filter-button-active");
        filterFullstack.getStyleClass().remove("filter-button-active");
        filterDevOps.getStyleClass().remove("filter-button-active");
        filterDesigner.getStyleClass().remove("filter-button-active");

        // Add active class to current filter
        switch (currentFilter) {
            case "All" -> filterAll.getStyleClass().add("filter-button-active");
            case "Frontend" -> filterFrontend.getStyleClass().add("filter-button-active");
            case "Backend" -> filterBackend.getStyleClass().add("filter-button-active");
            case "Fullstack" -> filterFullstack.getStyleClass().add("filter-button-active");
            case "DevOps" -> filterDevOps.getStyleClass().add("filter-button-active");
            case "Designer" -> filterDesigner.getStyleClass().add("filter-button-active");
        }
    }

    private void loadEngineers(String filterRole) {
        ceoCardContainer.getChildren().clear();
        foundersGrid.getChildren().clear();
        teamGrid.getChildren().clear();

        List<Engineer> allEngineers = DataStore.getInstance().getEngineers();

        // Apply filter
        List<Engineer> filtered = allEngineers.stream()
                .filter(e -> filterRole.equals("All") || e.getRole().toLowerCase().contains(filterRole.toLowerCase()))
                .collect(Collectors.toList());

        // Separate into categories
        List<Engineer> ceo = filtered.stream().filter(Engineer::isCeo).collect(Collectors.toList());
        List<Engineer> founders = filtered.stream().filter(e -> e.isFounder() && !e.isCeo())
                .collect(Collectors.toList());
        List<Engineer> team = filtered.stream().filter(e -> !e.isFounder() && !e.isCeo()).collect(Collectors.toList());

        // Show/hide sections based on content
        ceoSection.setVisible(!ceo.isEmpty());
        ceoSection.setManaged(!ceo.isEmpty());
        foundersSection.setVisible(!founders.isEmpty());
        foundersSection.setManaged(!founders.isEmpty());
        teamSection.setVisible(!team.isEmpty());
        teamSection.setManaged(!team.isEmpty());

        // Render CEO card
        for (Engineer eng : ceo) {
            ceoCardContainer.getChildren().add(createCeoCard(eng));
        }

        // Render founders
        for (Engineer eng : founders) {
            foundersGrid.getChildren().add(createEngineerCard(eng));
        }

        // Render team members
        for (Engineer eng : team) {
            teamGrid.getChildren().add(createEngineerCard(eng));
        }
    }

    private VBox createCeoCard(Engineer engineer) {
        VBox card = new VBox(20);
        card.getStyleClass().add("engineer-card-ceo");
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(600);
        card.setMaxWidth(700);

        // Large Image
        ImageView imageView = createImageView(engineer.getImageUrl(), 150);

        // Name
        Label name = new Label(engineer.getName());
        name.getStyleClass().add("engineer-name-ceo");

        // Role
        Label role = new Label(engineer.getRole());
        role.getStyleClass().add("engineer-role-ceo");

        // Bio
        Label bio = new Label(engineer.getBio());
        bio.getStyleClass().add("engineer-bio-ceo");
        bio.setWrapText(true);
        bio.setMaxWidth(500);
        bio.setStyle("-fx-text-alignment: center;");

        // Skills
        HBox skillsBox = new HBox(10);
        skillsBox.setAlignment(Pos.CENTER);
        for (String skill : engineer.getSkills()) {
            Label skillBadge = new Label(skill);
            skillBadge.getStyleClass().add("skill-badge-ceo");
            skillsBox.getChildren().add(skillBadge);
        }

        // Social buttons (UI only)
        HBox socialBox = new HBox(12);
        socialBox.setAlignment(Pos.CENTER);
        socialBox.setPadding(new Insets(10, 0, 0, 0));
        Button linkedIn = new Button("💼");
        linkedIn.getStyleClass().add("social-icon-btn");
        Button github = new Button("🐙");
        github.getStyleClass().add("social-icon-btn");
        Button twitter = new Button("🐦");
        twitter.getStyleClass().add("social-icon-btn");
        socialBox.getChildren().addAll(linkedIn, github, twitter);

        card.getChildren().addAll(imageView, name, role, bio, skillsBox, socialBox);

        card.setOnMouseClicked(e -> navigateToProfile(engineer));

        return card;
    }

    private VBox createEngineerCard(Engineer engineer) {
        VBox card = new VBox(12);
        card.getStyleClass().add("engineer-card");
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(250);
        card.setPrefHeight(320);

        // Image
        ImageView imageView = createImageView(engineer.getImageUrl(), 90);

        // Name
        Label name = new Label(engineer.getName());
        name.getStyleClass().add("engineer-name");

        // Role
        Label role = new Label(engineer.getRole());
        role.getStyleClass().add("engineer-role");

        // Bio
        Label bio = new Label(engineer.getBio());
        bio.getStyleClass().add("engineer-bio");
        bio.setWrapText(true);
        bio.setStyle("-fx-text-alignment: center;");
        bio.setMaxWidth(220);

        // Skills (first 3)
        HBox skillsBox = new HBox(6);
        skillsBox.setAlignment(Pos.CENTER);
        int maxSkills = Math.min(engineer.getSkills().size(), 3);
        for (int i = 0; i < maxSkills; i++) {
            Label skillBadge = new Label(engineer.getSkills().get(i));
            skillBadge.getStyleClass().add("skill-badge");
            skillsBox.getChildren().add(skillBadge);
        }

        card.getChildren().addAll(imageView, name, role, bio, skillsBox);

        card.setOnMouseClicked(e -> navigateToProfile(engineer));

        return card;
    }

    private ImageView createImageView(String imageUrl, int size) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(false);

        // Circle clip
        Circle clip = new Circle(size / 2.0, size / 2.0, size / 2.0);
        imageView.setClip(clip);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                Image image = null;
                if (imageUrl.startsWith("http")) {
                    image = new Image(imageUrl, true);
                } else {
                    String path = "/org/example/techhive_studio_website_project_final/images/engineers/" + imageUrl;
                    java.net.URL resource = getClass().getResource(path);
                    if (resource != null) {
                        image = new Image(resource.toExternalForm());
                    }
                }
                if (image != null) {
                    imageView.setImage(image);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return imageView;
    }

    private void navigateToProfile(Engineer engineer) {
        SceneManager.getInstance().switchViewWithFade("fxml/EngineerProfileView.fxml",
                (EngineerProfileController controller) -> {
                    controller.setEngineer(engineer);
                });
    }
}
